package es.uva.petadopt.controller;

import es.uva.petadopt.client.MascotaRestClient;
import es.uva.petadopt.model.Mascota;
import es.uva.petadopt.model.Refugio;
import es.uva.petadopt.model.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Named
@SessionScoped
public class RefugioBean implements Serializable {
    MascotaRestClient mascotaClient = new MascotaRestClient();
    
    private List<Mascota> mascotas;
    private Usuario usuario;
    private Refugio refugio;
    
    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession session = request.getSession(false);
        
        usuario = null;
        if (session != null) {
            usuario = (Usuario) session.getAttribute("usuarioLogueado");
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
    
    public void buscarMascotas() {
        mascotas = mascotaClient.findByRefugio(refugio.getEmail());
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