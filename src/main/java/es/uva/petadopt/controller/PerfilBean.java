
package es.uva.petadopt.controller;

import es.uva.petadopt.client.UsuarioRestClient;
import es.uva.petadopt.model.Cliente;
import es.uva.petadopt.model.Refugio;
import es.uva.petadopt.model.Usuario;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.model.file.UploadedFile;

@Named
@RequestScoped
public class PerfilBean {    
    private final UsuarioRestClient usuarioRest = new UsuarioRestClient();
    
    private Usuario usuario;
    private Cliente cliente;
    private Refugio refugio;
    
    private String email;
    private UploadedFile imagen;
    
    private String nombreCliente;
    private String apellidosCliente;
    
    private String nombreRefugio;
    private String direccion;
    private String telefono;

    private String currentPassword = null;
    private String newPassword = null;
    private String confirmPassword = null;
    
    @PostConstruct
    public void init(){
        setDataUsuario();
    }
    
    public String eliminarCuenta(){
        new UsuarioRestClient().delete(usuario);
        return new LoginBean().logout();
    }
    
    public String getUrlImagenPerfil(String email) {
        return "/webresources/usuarios/imagen/" + email;
    }
    
    public void setDataUsuario(){
        usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogueado");
        
        if(usuario.getTipo().equals("cliente")){
            setDataCliente();
        }else if(usuario.getTipo().equals("refugio")){
            setDataRefugio();
        }
    }
    
    public void setDataRefugio(){
        refugio = (Refugio)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("refugioLogueado");
        nombreRefugio = refugio.getNombreRefugio();
        direccion = refugio.getDireccion();
        telefono = refugio.getTelefono();
        email = refugio.getEmail();
    }
    
    public void setDataCliente(){
        cliente = (Cliente)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("clienteLogueado");
        nombreCliente = cliente.getNombre();
        apellidosCliente = cliente.getApellidos();
        email = cliente.getEmail();
    }
    
    public void showChangePasswordDialog() {
        PrimeFaces.current().executeScript("PF('changePasswordDialog').show()");
    }
    
    public String changePassword() {
        if (usuarioRest.checkPassword(email, currentPassword) && newPassword.equals(confirmPassword)) {
            usuarioRest.changePassword(email, newPassword);

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
    
    public String getNombreRefugio() {
        return nombreRefugio;
    }

    public UploadedFile getImagen() {
        return imagen;
    }

    public void setImagen(UploadedFile imagen) {
        this.imagen = imagen;
    }
    
    public void cambiarFotoPerfil(){
        if (imagen != null) {
            usuario.setFoto(imagen.getContent());
            usuarioRest.update(usuario);
        }
    }

    public void setNombreRefugio(String nombreRefugio) {
        this.nombreRefugio = nombreRefugio;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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
