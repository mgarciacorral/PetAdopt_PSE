package es.uva.petadopt.controller;

import es.uva.petadopt.dao.ClienteDao;
import es.uva.petadopt.dao.RefugioDao;
import es.uva.petadopt.dao.UsuarioDao;
import es.uva.petadopt.model.Cliente;
import es.uva.petadopt.model.Refugio;
import es.uva.petadopt.model.Usuario;


import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class LoginBean implements Serializable {

    @Inject
    private UsuarioDao usuarioDao; 
    
    @Inject
    private ClienteDao clienteDao;
    
    @Inject
    private RefugioDao refugioDao;

    private String email;
    private String password;
    private String tipoUsuario;
    private boolean autenticado;
    
    private Cliente cliente;
    private Refugio refugio;
    private Usuario usuario;

    public String login() {
        usuario = usuarioDao.findByEmail(email);

        if (usuario != null && usuarioDao.checkPassword(password, usuario.getPassword())) {
            tipoUsuario = usuario.getTipo();
            autenticado = true;
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuarioLogueado", usuario);
            
            if (null != tipoUsuario) switch (tipoUsuario) {
                case "admin":
                    return "/admin/panel.xhtml?faces-redirect=true";
                case "cliente":
                    cliente = clienteDao.findByEmail(email);
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("clienteLogueado", cliente);
                    return "/cliente/buscar.xhtml?faces-redirect=true";
                case "refugio":
                    refugio = refugioDao.findByEmail(email);
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("refugioLogueado", refugio);
                    return "/refugio/mascotas.xhtml?faces-redirect=true";
                default:
                    break;
            }
        }

        return "/login_error.xhtml?faces-redirect=true";
    }

    public String logout() {
        email = null;
        password = null;
        tipoUsuario = null;
        autenticado = false;
        cliente = null;
        refugio = null;
        usuario = null;
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();            
        return "/index.xhtml?faces-redirect=true";
    }


    // Getters y Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public boolean isAutenticado() {
        return autenticado;
    }
}