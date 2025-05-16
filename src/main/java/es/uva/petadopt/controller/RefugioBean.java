package es.uva.petadopt.controller;

import es.uva.petadopt.client.ChatRestClient;
import es.uva.petadopt.client.MascotaRestClient;
import es.uva.petadopt.client.SolicitudRestClient;
import es.uva.petadopt.model.Cliente;
import es.uva.petadopt.model.Mascota;
import es.uva.petadopt.model.Refugio;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private Refugio refugio;
    private Mascota selectedMascota;
    private final ChatRestClient chatRest = new ChatRestClient();
    
    private final Map<Integer, Boolean> confirmacionEliminar = new HashMap<>();

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
        } else {
            buscarMascotas();
        }
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
        
        Cliente cliente = new Cliente();

        return chatRest.findChat(cliente.getEmail(), selectedMascota.getEmailRefugio());

    }
    
    public String verChat() {
        return "/cliente/chat.xhtml?faces-redirect=true&idChat=" + cargarChat();

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

    public void verMascota(int id) {
        this.selectedMascota = mascotaClient.find(id);
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