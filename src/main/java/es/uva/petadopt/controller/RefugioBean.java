package es.uva.petadopt.controller;

import es.uva.petadopt.client.MascotaRestClient;
import es.uva.petadopt.model.Mascota;
import es.uva.petadopt.model.Refugio;
import java.io.Serializable;
import java.util.List;
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
    
    private List<Mascota> mascotas;
    private Refugio refugio;
    private Mascota selectedMascota;
    
    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession session = request.getSession(false);
        
        if (session != null) {
            refugio = (Refugio) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("refugioLogueado");

        } 
        
        buscarMascotas();
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
    
    public void eliminarMascota(Mascota mascota){
        mascotaClient.borrarMascota(mascota);
    }
    
    public void editarMascota(Mascota mascota){
        
    }

    public void setSelectedMascota(Mascota selectedMascota) {
        this.selectedMascota = selectedMascota;
    }
    
    public void buscarMascotas() {
        mascotas = mascotaClient.findByRefugio(refugio.getEmail());
    }
    
    public String getUrlImagenMascota(Integer idMascota) {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()
                + "/webresources/mascota/" + idMascota + "/imagen";
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