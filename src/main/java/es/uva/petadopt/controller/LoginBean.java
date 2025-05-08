package es.uva.petadopt.controller;

import es.uva.petadopt.dao.UsuarioDao;
import es.uva.petadopt.model.Usuario;


import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class LoginBean implements Serializable {

    @Inject
    private UsuarioDao usuarioDao;  // Inyectamos el DAO para buscar usuarios

    private String email;
    private String password;
    private String tipoUsuario;
    private boolean autenticado;

    public String login() {
        Usuario usuario = usuarioDao.findByEmail(email);

        if (usuario != null && usuarioDao.checkPassword(password, usuario.getPassword())) {
            tipoUsuario = usuario.getTipo();
            autenticado = true;

            if (null != tipoUsuario) switch (tipoUsuario) {
                case "admin":
                    return "/admin/panel.xhtml?faces-redirect=true";
                case "cliente":
                    return "/cliente/buscar.xhtml?faces-redirect=true";
                case "refugio":
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
