package es.uva.petadopt.controller;

import es.uva.petadopt.client.ChatRestClient;
import es.uva.petadopt.client.MascotaRestClient;
import es.uva.petadopt.client.SolicitudRestClient;
import es.uva.petadopt.model.Mascota;
import es.uva.petadopt.model.Cliente;
import es.uva.petadopt.model.Solicitudadopcion;
import es.uva.petadopt.model.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
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
    private Usuario usuario;
    private Cliente cliente;

    
    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession session = request.getSession(false); // false para no crear una nueva si no existe
        
        usuario = null;
        if (session != null) {
            usuario = (Usuario) session.getAttribute("usuarioLogueado");
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
    
    private void buscarMascotasSolicitadas() {
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
            mascotas = solicitudClient.findSolicitadas(cliente.getEmail());
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
    
    public void solicitarMascota(){
        
        if(solicitudClient.comprobarSolicitud(cliente.getEmail(), selectedMascota.getIdMascota())){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Solicitud enviada", "Hemos enviado tu solicitud al refugio."));

            solicitudClient.createSolicitud(cliente, selectedMascota);
            Solicitudadopcion solicitud = solicitudClient.getLastSolicitudId(cliente.getEmail(), selectedMascota.getIdMascota());
            chatRest.createChat(cliente.getEmail(), selectedMascota.getRefugio().getEmail() , solicitud.getIdSolicitud());
            
        }else{
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "Ya se ha solicitado está mascota"));
        }
        
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
    
    public String verChats(){
        return "/cliente/chat.xhtml?faces-redirect=true";
    }

    // Getters y setters
    public Usuario getUsuario(){
        return usuario;
    }
    
    public void setUsuario(Usuario usuario){
        this.usuario = usuario;
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
    