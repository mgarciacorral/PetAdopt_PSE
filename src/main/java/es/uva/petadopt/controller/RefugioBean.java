package es.uva.petadopt.controller;

import es.uva.petadopt.client.ChatRestClient;
import es.uva.petadopt.client.MascotaRestClient;
import es.uva.petadopt.client.SolicitudRestClient;
import es.uva.petadopt.dto.SolicitudMascotaDTO;
import es.uva.petadopt.model.Mascota;
import es.uva.petadopt.model.Refugio;
import es.uva.petadopt.model.Solicitudadopcion;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Named
@ViewScoped
public class RefugioBean implements Serializable {
    MascotaRestClient mascotaClient = new MascotaRestClient();
    SolicitudRestClient solicitudClient = new SolicitudRestClient();
    
    private List<Mascota> mascotas;
    private List<Solicitudadopcion> solicitudes;
    private Refugio refugio;
    private Mascota selectedMascota;
    private final ChatRestClient chatRest = new ChatRestClient();
    
    private final Map<Integer, Boolean> confirmacionEliminar = new HashMap<>();
    
    private List<SolicitudMascotaDTO> solicitudesConMascota = new ArrayList<>();
    private SolicitudMascotaDTO selectedSolicitudDto;
    private Solicitudadopcion selectedSolicitud;

    public boolean isConfirmandoEliminar(int mascotaId) {
        return confirmacionEliminar.getOrDefault(mascotaId, false);
    }

    public void prepararConfirmacion(Mascota mascota) {
        confirmacionEliminar.put(mascota.getIdMascota(), true);
    }

    public void eliminarMascota(Mascota mascota) {
        confirmacionEliminar.remove(mascota.getIdMascota());
        mascotaClient.borrarMascota(mascota);
    }
    
    public boolean isAutorizado(){
        return refugio.getAutorizado();
    }
    
    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession session = request.getSession(false);
        
        if (session != null) {
            refugio = (Refugio) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("refugioLogueado");
        } 
        
        String viewId = context.getViewRoot().getViewId();

        if (viewId.contains("solicitudes.xhtml")) {
            mascotas = solicitudClient.findMascotasSolicitadas(refugio.getEmail());
            solicitudes = solicitudClient.findSolicitudesSolicitadas(refugio.getEmail());
            
            Map<Integer, Mascota> mapaMascotas = mascotas.stream()
                    .collect(Collectors.toMap(Mascota::getIdMascota, m -> m));
            
            solicitudes.forEach(solicitud -> {
                Mascota mascota = mapaMascotas.get(solicitud.getIdMascota());
                solicitudesConMascota.add(new SolicitudMascotaDTO(solicitud, mascota));
            });

            
        } else {
            buscarMascotas();
        }
    }
    
    

    public SolicitudMascotaDTO getSelectedSolicitudDto() {
        return selectedSolicitudDto;
    }
    
    public void aceptarSolicitud(){
        mascotaClient.borrarMascota(selectedMascota);
    }
    
    public void rechazarSolicitud(){
        solicitudClient.borrarSolicitud(selectedSolicitud);
    }
    
    public boolean verificarUsuario(String email) {
        try {
            String urlString = "http://serpis.infor.uva.es:80/darklist/api/validar_adoptante/" + email;
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int status = con.getResponseCode();

            if (status == 404) {
                return true;
            }

            if (status == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                in.close();

                String json = response.toString();
                return json.contains("\"listaNegra\":\"no\"");
            }

            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Mascota findMascota(int idMascota){

        return mascotaClient.find(idMascota);
    }
    
    public void setSelectedSolicitudDto(SolicitudMascotaDTO selectedSolicitudDto) {
        this.selectedSolicitudDto = selectedSolicitudDto;
    }

    public Solicitudadopcion getSelectedSolicitud() {
        return selectedSolicitud;
    }

    public void setSelectedSolicitud(Solicitudadopcion selectedSolicitud) {
        this.selectedSolicitud = selectedSolicitud;
    }  
    
    public MascotaRestClient getMascotaClient() {
        return mascotaClient;
    }

    public void setMascotaClient(MascotaRestClient mascotaClient) {
        this.mascotaClient = mascotaClient;
    }

    public List<Solicitudadopcion> getSolicitudes() {
        return solicitudes;
    }

    public void setSolicitudes(List<Solicitudadopcion> solicitudes) {
        this.solicitudes = solicitudes;
    }

    public Refugio getRefugio() {
        return refugio;
    }

    public void setRefugio(Refugio refugio) {
        this.refugio = refugio;
    }

    public List<SolicitudMascotaDTO> getSolicitudesConMascota() {
        return solicitudesConMascota;
    }

    public void setSolicitudesConMascota(List<SolicitudMascotaDTO> solicitudesConMascota) {
        this.solicitudesConMascota = solicitudesConMascota;
    }
    
    
    public List<Mascota> getMascotas() {
        return mascotas;
    }

    public void setMascotas(List<Mascota> mascotas) {
        this.mascotas = mascotas;
    }

    public Mascota getSelectedMascota() {
        return selectedMascota;
    }
    
    public String editarMascota(Mascota mascota) {
        System.out.println("agregarMascota?faces-redirect=true&includeViewParams=true&mascotaId=" + mascota.getIdMascota());
        return "agregarMascota?faces-redirect=true&includeViewParams=true&mascotaId=" + mascota.getIdMascota();
    }
    
    public int cargarChat() {

        return chatRest.findChat(selectedSolicitud.getEmailCliente(), selectedMascota.getEmailRefugio());

    }
    
    public String verChat() {
        return "/refugio/chat.xhtml?faces-redirect=true&idChat=" + cargarChat();

    }

    public void setSelectedMascota(Mascota selectedMascota) {
        this.selectedMascota = selectedMascota;
    }
    
    public void buscarMascotas() {
        mascotas = mascotaClient.findByRefugio(refugio.getEmail());
    }
    
    public String getUrlImagenMascota(Integer idMascota) {
        return "/webresources/mascotas/imagen/" + idMascota;
    }

    public void verSolicitud(int id) {
        selectedSolicitud = solicitudClient.find(id);
        selectedMascota = mascotaClient.find(selectedSolicitud.getIdMascota());
    }
    
    public String verPaginaGestion() {
        return "/refugio/mascotas.xhtml?faces-redirect=true";
    }
    
    public String verSolicitudes() {
        return "/refugio/solicitudes.xhtml?faces-redirect=true";
    }
    
    public String verPerfil() {
        return "/refugio/perfil.xhtml?faces-redirect=true";
    }
}