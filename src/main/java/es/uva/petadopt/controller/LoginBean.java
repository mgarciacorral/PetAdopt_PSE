package es.uva.petadopt.controller;

import es.uva.petadopt.client.ClienteRestClient;
import es.uva.petadopt.client.RefugioRestClient;
import es.uva.petadopt.jaas.UserEJB;
import es.uva.petadopt.model.Usuario;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Named
@RequestScoped
public class LoginBean {    
    @Inject
    UserEJB userEJB;
    
    RefugioRestClient refugioClient = new RefugioRestClient();
    ClienteRestClient clienteClient = new ClienteRestClient();
    
    
    private Usuario user;

    private String email;
    private String password;
    private String tipoUsuario;
    private boolean autenticado;

    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            request.login(email, password);
        } catch (ServletException e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Login incorrecto!", null));
            return "login";
        }

        this.user = userEJB.findByEmail(request.getUserPrincipal().getName());
        request.getSession().setAttribute("usuarioLogueado", user);
        if (request.isUserInRole("cliente")) {
            request.getSession().setAttribute("clienteLogueado", clienteClient.findByEmail(user.getEmail()));
            return "/cliente/buscar.xhtml?faces-redirect=true";
        } else if (request.isUserInRole("refugio")) {
            request.getSession().setAttribute("refugioLogueado", refugioClient.findByEmail(user.getEmail()));
            return "/refugio/mascotas.xhtml?faces-redirect=true";
        } else if (request.isUserInRole("admin")) {
            return "/admin/panel.xhtml?faces-redirect=true";
        }else {
            return "login";
        }
    }

    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            this.user = null;
            request.logout();
            ((HttpSession) context.getExternalContext().getSession(false)).invalidate();
        } catch (ServletException e) {
            System.out.println("Fallo durante el proceso de logout!");
        }
        return "/index?faces-redirect=true";
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
    
    public Usuario getAuthenticatedUser() {
        return user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAutenticado() {
        return autenticado;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }
}