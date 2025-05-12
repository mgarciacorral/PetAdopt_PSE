package es.uva.petadopt.controller;

import es.uva.petadopt.dao.ClienteDao;
import es.uva.petadopt.dao.RefugioDao;
import es.uva.petadopt.dao.UsuarioDao;
import es.uva.petadopt.model.Cliente;
import es.uva.petadopt.model.Refugio;
import es.uva.petadopt.model.Usuario;


import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.PrimeFaces;

@Named
@RequestScoped
public class LoginBean implements Serializable {
    @Inject
    private UsuarioDao usuarioDao; 
    
    @Inject
    private ClienteDao clienteDao;
    
    @Inject
    private RefugioDao refugioDao;
    
    private Cliente cliente;
    private String nombreCliente;
    private String apellidosCliente;
    
    private Refugio refugio;
    
    private Usuario usuario;
    private String email;
    private String password;
    private String tipoUsuario;
    private boolean autenticado;
    
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;

    public String login() {
        usuario = usuarioDao.findByEmail(email);

        if (usuario != null && usuarioDao.checkPassword(password, usuario.getPassword())) {
            tipoUsuario = usuario.getTipo();
            autenticado = true;

            if (null != tipoUsuario) switch (tipoUsuario) {
                case "admin":
                    return "/admin/panel.xhtml?faces-redirect=true";
                case "cliente":
                    cliente = clienteDao.findByEmail(email);
                    setDatosCliente();
                    return "/cliente/buscar.xhtml?faces-redirect=true";
                case "refugio":
                    refugio = refugioDao.findByEmail(email);
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
        nombreCliente = null;
        apellidosCliente = null;
        refugio = null;
        usuario = null;
                
        return "/index.xhtml?faces-redirect=true";
    }
    
    public void showChangePasswordDialog() {
        PrimeFaces.current().executeScript("PF('changePasswordDialog').show()");
    }
    
    public String changePassword() {
        if (usuarioDao.checkPassword(currentPassword, usuario.getPassword()) && newPassword.equals(confirmPassword)) {
            usuario.setPassword(newPassword);

            usuarioDao.save(usuario);

            currentPassword = null;
            newPassword = null;
            confirmPassword = null;
            usuario.setPassword("brr");

            FacesMessage msg = new FacesMessage("Contraseña cambiada con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);

            PrimeFaces.current().executeScript("PF('changePasswordDialog').hide()");

            return null;
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Contraseña actual incorrecta o nueva contraseña no coincide.", null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;
        }
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
    
    public void setDatosCliente(){
        nombreCliente = cliente.getNombre();
        apellidosCliente = cliente.getApellidos();
    }
    
    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getApellidosCliente() {
        return apellidosCliente;
    }

    public void setApellidosCliente(String apellidosCliente) {
        this.apellidosCliente = apellidosCliente;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
