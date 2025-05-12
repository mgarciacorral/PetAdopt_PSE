package es.uva.petadopt.controller;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class RefugioBean implements Serializable {
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