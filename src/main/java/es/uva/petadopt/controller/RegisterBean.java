package es.uva.petadopt.controller;

import es.uva.petadopt.dao.ClienteDao;
import es.uva.petadopt.dao.RefugioDao;
import es.uva.petadopt.dao.UsuarioDao;
import es.uva.petadopt.model.Cliente;
import es.uva.petadopt.model.Refugio;
import es.uva.petadopt.model.Usuario;

import java.io.Serializable;
import java.util.Date;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class RegisterBean implements Serializable {

    @Inject
    private UsuarioDao usuarioDao;

    @Inject
    private ClienteDao clienteDao;

    @Inject
    private RefugioDao refugioDao;

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

    // Métodos de registro
    public String register() {
        try {
            System.out.println(userType);
            Usuario existingUser = usuarioDao.findByEmail(email);
            if (existingUser != null) {
                return "/registro_error.xhtml?faces-redirect=true";
            }

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

                usuarioDao.save(usuario);
                clienteDao.save(cliente);
            } else if ("refugio".equals(userType)) {
                usuario.setTipo("refugio");
                Refugio refugio = new Refugio();

                refugio.setNombreRefugio(nombreRefugio);
                refugio.setEmail(email);
                refugio.setCif(cif);
                refugio.setTelefono(telefono);
                refugio.setDireccion(direccion);
                refugio.setAutorizado(Boolean.FALSE);

                usuarioDao.save(usuario);
                refugioDao.save(refugio);
            }
            
            return "/auth/login.xhtml?faces-redirect=true";
        } catch (IllegalArgumentException e) {
            return "/registro_error.xhtml?faces-redirect=true";
        }
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
