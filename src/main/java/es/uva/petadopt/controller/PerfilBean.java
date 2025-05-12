
package es.uva.petadopt.controller;

import es.uva.petadopt.dao.UsuarioDao;
import es.uva.petadopt.model.Cliente;
import es.uva.petadopt.model.Usuario;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.PrimeFaces;

@Named
@RequestScoped
public class PerfilBean {
    @Inject
    private UsuarioDao usuarioDao; 
    
    private Usuario usuario;
    private Cliente cliente;
    
    private String nombreCliente;
    private String apellidosCliente;
    private String email;

    private String currentPassword = null;
    private String newPassword = null;
    private String confirmPassword = null;
    
    @PostConstruct
    public void init(){
        cliente = (Cliente)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("clienteLogueado");
        usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogueado");
        setDataCliente();
    }
    
    public void setDataCliente(){
        nombreCliente = cliente.getNombre();
        apellidosCliente = cliente.getApellidos();
        email = cliente.getEmail();
    }
    
    public void showChangePasswordDialog() {
        PrimeFaces.current().executeScript("PF('changePasswordDialog').show()");
    }
    
    public String changePassword() {
        if (usuarioDao.checkPassword(currentPassword, usuario.getPassword()) && newPassword.equals(confirmPassword)) {
            usuarioDao.changePasswordByEmail(email, newPassword);

            currentPassword = null;
            newPassword = null;
            confirmPassword = null;

            FacesMessage msg = new FacesMessage("Contraseña cambiada con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);

            return null;
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Contraseña actual incorrecta o nueva contraseña no coincide.", null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;
        }
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
