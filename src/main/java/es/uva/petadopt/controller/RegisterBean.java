package es.uva.petadopt.controller;

import es.uva.petadopt.jaas.UserEJB;
import es.uva.petadopt.model.Cliente;
import es.uva.petadopt.model.Refugio;
import es.uva.petadopt.model.Usuario;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class RegisterBean implements Serializable {   
    @Inject
    private UserEJB userEJB;

    private String email;
    private String password;
    private String nombre;
    private String apellidos;
    private String nif;
    private String domicilio;
    private String telefono;
    private Date fechaNacimiento;
    private String nombreRefugio;
    private String cif;
    private String direccion;
    private String userType = "cliente";

    public String register() {
        try {
            Usuario usuario = new Usuario();   
            usuario.setEmail(email);
            usuario.setPassword(password);

            if ("cliente".equals(userType)) {
                usuario.setTipo("cliente");
                Cliente cliente = new Cliente();

                cliente.setEmail(email);
                cliente.setNombre(nombre);
                cliente.setApellidos(apellidos);
                cliente.setDomicilio(domicilio);
                cliente.setNif(nif);
                cliente.setFechaNacimiento(fechaNacimiento);
                cliente.setTelefono(telefono);

                userEJB.createCliente(usuario, cliente);
            } else if ("refugio".equals(userType)) {
                usuario.setTipo("refugio");
                Refugio refugio = new Refugio();

                refugio.setNombreRefugio(nombreRefugio);
                refugio.setEmail(email);
                refugio.setCif(cif);
                refugio.setTelefono(telefono);
                refugio.setDireccion(direccion);
                refugio.setAutorizado(Boolean.FALSE);

                userEJB.createRefugio(usuario, refugio);
            }
            
            return "/auth/login.xhtml?faces-redirect=true";
        } catch (IllegalArgumentException e) {
            return "/registro.xhtml?faces-redirect=true";
        }
    }
    
    public void validatePassword(ComponentSystemEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIComponent components = event.getComponent();
        UIInput uiInputPassword = (UIInput) components.findComponent("password");
        String password = uiInputPassword.getLocalValue() == null ? "" : uiInputPassword.getLocalValue().toString();
       
        if (password.isEmpty()) {
            return;
        }
        
        UIInput uiInputEmail = (UIInput) components.findComponent("email");
        String email = uiInputEmail.getLocalValue() == null ? ""
                : uiInputEmail.getLocalValue().toString();
        if (userEJB.findByEmail(email) != null) {
            FacesMessage msg = new FacesMessage("Ya existe un usuario con ese email");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            facesContext.addMessage(uiInputPassword.getClientId(), msg);
            facesContext.renderResponse();
        }
    }
    
    public Date getFechaMaximaNacimiento() {
        LocalDate hoyMenos18 = LocalDate.now().minusYears(18);
        return Date.from(hoyMenos18.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
    
    public void onUserTypeChange() {
        System.out.println("Nuevo tipo de usuario: " + userType);
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNombreRefugio() {
        return nombreRefugio;
    }

    public void setNombreRefugio(String nombreRefugio) {
        this.nombreRefugio = nombreRefugio;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
