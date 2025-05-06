package es.uva.petadopt.controller;


import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class LoginBean implements Serializable {

    private String email;
    private String password;
    private String tipoUsuario; // cliente, refugio, admin
    private boolean autenticado;

    public String login() {
        // LOGIN TEMPORAL: reemplazar con verificación real
        if (email.equals("admin@petadopt.com") && password.equals("admin123")) {
            tipoUsuario = "admin";
            autenticado = true;
            return "/admin/panel.xhtml?faces-redirect=true";
        } else if (email.equals("ana.cliente@gmail.com") && password.equals("passAna")) {
            tipoUsuario = "cliente";
            autenticado = true;
            return "/cliente/buscar.xhtml?faces-redirect=true";
        } else if (email.equals("refugio.amigos@gmail.com") && password.equals("refugio123")) {
            tipoUsuario = "refugio";
            autenticado = true;
            return "/refugio/mascotas.xhtml?faces-redirect=true";
        } else {
            return "/login_error.xhtml?faces-redirect=true";
        }
    }

    public String logout() {
        email = null;
        password = null;
        tipoUsuario = null;
        autenticado = false;
        return "/index.xhtml?faces-redirect=true";
    }

    // Getters y setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getTipoUsuario() { return tipoUsuario; }
    public boolean isAutenticado() { return autenticado; }
}
