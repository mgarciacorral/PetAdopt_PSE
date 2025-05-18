package es.uva.petadopt.controller;

import es.uva.petadopt.client.ChatRestClient;
import es.uva.petadopt.client.MascotaRestClient;
import es.uva.petadopt.client.SolicitudRestClient;
import es.uva.petadopt.model.Mascota;
import es.uva.petadopt.model.Cliente;
import es.uva.petadopt.model.Solicitudadopcion;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Named
@ViewScoped
public class ClienteBean implements Serializable {
    SolicitudRestClient solicitudClient = new SolicitudRestClient();
    MascotaRestClient mascotaClient = new MascotaRestClient();
    private final ChatRestClient chatRest = new ChatRestClient();
    

    private String selectedEspecie;
    private String selectedRaza;
    private Mascota selectedMascota;
    private String filtroBusqueda;
    private List<Mascota> mascotas;
    private List<String> especies;
    private List<String> razas;
    private Cliente cliente;

    
    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession session = request.getSession(false); // false para no crear una nueva si no existe
        
        if (session != null) {
            cliente = (Cliente) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("clienteLogueado");

        }
        String viewId = context.getViewRoot().getViewId();

        if (viewId.contains("solicitudes.xhtml")) {
            mascotas = solicitudClient.findSolicitadas(cliente.getEmail());
        } else {
            buscarMascotas();
        }

        cargarEspecies();
    }

    
    // Método para buscar las mascotas
    public void buscarMascotas() {
        // Realiza la búsqueda con los filtros seleccionados
        if (selectedEspecie != null && !selectedEspecie.isEmpty()) {
            mascotas = mascotaClient.findByEspecie(selectedEspecie);
        }
        if (selectedRaza != null && !selectedRaza.isEmpty()) {
            mascotas = mascotaClient.findByRaza(selectedRaza);
        }
        
        // Si no se seleccionan filtros, mostramos todas las mascotas
        if ((selectedEspecie == null || selectedEspecie.isEmpty()) && 
            (selectedRaza == null || selectedRaza.isEmpty())) {
            mascotas = mascotaClient.findAll();
        }
    }
    
    public void buscarPorNombre(){
        if (filtroBusqueda == null || filtroBusqueda.isEmpty()) {
            // Si no hay filtro, mostrar todas las mascotas
            mascotas = mascotaClient.findAll();
        } else {
            // Si hay filtro, buscar mascotas por nombre
            mascotas = mascotaClient.findByNombre(filtroBusqueda);

        }
    }
    
    public void cargarEspecies(){
        this.especies = mascotaClient.obtenerEspecies();
    }
    
    public void cargarRazas(){
        if (selectedEspecie != null && !selectedEspecie.isEmpty()) {
            this.razas = mascotaClient.obtenerRazasPorEspecie(selectedEspecie); // Obtener razas según la especie
        }
    }

    public void verMascota(int id) {
        this.selectedMascota = mascotaClient.find(id);
    }
    
    public String solicitarMascota(){
        if(solicitudClient.comprobarSolicitud(cliente.getEmail(), selectedMascota.getIdMascota())){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Solicitud enviada", "Hemos enviado tu solicitud al refugio."));

            solicitudClient.createSolicitud(cliente, selectedMascota);
            Solicitudadopcion solicitud = solicitudClient.getLastSolicitudId(cliente.getEmail(), selectedMascota.getIdMascota());
            
            if(chatRest.findChat(cliente.getEmail(), selectedMascota.getEmailRefugio()) == -1){
                chatRest.createChat(cliente.getEmail(), selectedMascota.getEmailRefugio(), solicitud.getIdSolicitud());
            }else{
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Ya tienes un chat con este refugio"));
            }
            return "/cliente/confirmacionSolicitud.xhtml?faces-redirect=true&mascotaId=" + selectedMascota.getIdMascota();
            
        }else{
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "Ya has solicitado está mascota"));
            return null;
        }
    }
    
    public Solicitudadopcion getLastSolicitud(){
        return solicitudClient.getLastSolicitudId(cliente.getEmail(), selectedMascota.getIdMascota());
    }
    
    public int cargarChat(){
        
        return chatRest.findChat(cliente.getEmail(), selectedMascota.getEmailRefugio());
  
    }
    
    public String getUrlImagenMascota(Integer idMascota) {
        return "/webresources/mascotas/imagen/" + idMascota;
    }
    
    public String verPaginaBusqueda() {
        return "/cliente/buscar.xhtml?faces-redirect=true";
    }
    
    public String verSolicitudes() {
        return "/cliente/solicitudes.xhtml?faces-redirect=true";
    }
    
    public String verPerfil() {
        return "/cliente/perfil.xhtml?faces-redirect=true";
    }
    
    public String verChat(){
        return "/cliente/chat.xhtml?faces-redirect=true&idChat=" + cargarChat();   
    }
    
    public String getSelectedEspecie() {
        return selectedEspecie;
    }

    public void setSelectedEspecie(String selectedEspecie) {
        this.selectedEspecie = selectedEspecie;
    }
    
    public Mascota getSelectedMascota(){
        return selectedMascota;
    }
    
    public void setSelectedMascota(Mascota selectedMascota){
       this.selectedMascota = selectedMascota;
    }
    
    public String getFiltroBusqueda() {
        return filtroBusqueda;
    }

    public void setFiltroBusqueda(String filtroBusqueda) {
        this.filtroBusqueda = filtroBusqueda;
    }

    public String getSelectedRaza() {
        return selectedRaza;
    }

    public void setSelectedRaza(String selectedRaza) {
        this.selectedRaza = selectedRaza;
    }

    public List<Mascota> getMascotas() {
        return mascotas;
    }

    public void setMascotas(List<Mascota> mascotas) {
        this.mascotas = mascotas;
    }
    
    public List<String> getEspecies() {
        return especies;
    }

    public void setEspecies(List<String> especies) {
        this.especies = especies;
    }
    
    public List<String> getRazas(){
        return razas;
    }
    
    public void setRazas(List<String> razas){
        this.razas = razas;
    }
}
    