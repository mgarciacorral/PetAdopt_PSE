/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.petadopt.model;

import java.io.Serializable;
import java.util.Collection;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mgarc
 */
@Entity
@Table(name = "refugio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Refugio.findAll", query = "SELECT r FROM Refugio r"),
    @NamedQuery(name = "Refugio.findByEmail", query = "SELECT r FROM Refugio r WHERE r.email = :email"),
    @NamedQuery(name = "Refugio.findByNombreRefugio", query = "SELECT r FROM Refugio r WHERE r.nombreRefugio = :nombreRefugio"),
    @NamedQuery(name = "Refugio.findByCif", query = "SELECT r FROM Refugio r WHERE r.cif = :cif"),
    @NamedQuery(name = "Refugio.findByDireccion", query = "SELECT r FROM Refugio r WHERE r.direccion = :direccion"),
    @NamedQuery(name = "Refugio.findByTelefono", query = "SELECT r FROM Refugio r WHERE r.telefono = :telefono"),
    @NamedQuery(name = "Refugio.findByAutorizado", query = "SELECT r FROM Refugio r WHERE r.autorizado = :autorizado")})
public class Refugio implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre_refugio")
    private String nombreRefugio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 9)
    @Column(name = "cif")
    private String cif;
    @Size(max = 255)
    @Column(name = "direccion")
    private String direccion;
    @Size(max = 15)
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "autorizado")
    private Boolean autorizado;
    @OneToMany(mappedBy = "emailRefugio")
    private Collection<Chat> chatCollection;
    @OneToMany(mappedBy = "emailRefugio")
    private Collection<Mascota> mascotaCollection;
    @JoinColumn(name = "email", referencedColumnName = "email", insertable = false, updatable = false)
    @JsonbTransient
    @OneToOne(optional = false)
    private Usuario usuario;

    public Refugio() {
    }

    public Refugio(String email) {
        this.email = email;
    }

    public Refugio(String email, String nombreRefugio, String cif) {
        this.email = email;
        this.nombreRefugio = nombreRefugio;
        this.cif = cif;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Boolean getAutorizado() {
        return autorizado;
    }

    public void setAutorizado(Boolean autorizado) {
        this.autorizado = autorizado;
    }

    @XmlTransient
    public Collection<Chat> getChatCollection() {
        return chatCollection;
    }

    public void setChatCollection(Collection<Chat> chatCollection) {
        this.chatCollection = chatCollection;
    }

    @XmlTransient
    public Collection<Mascota> getMascotaCollection() {
        return mascotaCollection;
    }

    public void setMascotaCollection(Collection<Mascota> mascotaCollection) {
        this.mascotaCollection = mascotaCollection;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (email != null ? email.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Refugio)) {
            return false;
        }
        Refugio other = (Refugio) object;
        if ((this.email == null && other.email != null) || (this.email != null && !this.email.equals(other.email))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.uva.petadopt.model.Refugio[ email=" + email + " ]";
    }
    
}
