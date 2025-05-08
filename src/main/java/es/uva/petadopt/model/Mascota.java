/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.petadopt.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "mascota")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mascota.findAll", query = "SELECT m FROM Mascota m"),
    @NamedQuery(name = "Mascota.findByIdMascota", query = "SELECT m FROM Mascota m WHERE m.idMascota = :idMascota"),
    @NamedQuery(name = "Mascota.findByNombre", query = "SELECT m FROM Mascota m WHERE m.nombre = :nombre"),
    @NamedQuery(name = "Mascota.findByEspecie", query = "SELECT m FROM Mascota m WHERE m.especie = :especie"),
    @NamedQuery(name = "Mascota.findByRaza", query = "SELECT m FROM Mascota m WHERE m.raza = :raza"),
    @NamedQuery(name = "Mascota.findByEdad", query = "SELECT m FROM Mascota m WHERE m.edad = :edad"),
    @NamedQuery(name = "Mascota.findByFoto", query = "SELECT m FROM Mascota m WHERE m.foto = :foto")})
public class Mascota implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_mascota")
    private Integer idMascota;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "especie")
    private String especie;
    @Size(max = 100)
    @Column(name = "raza")
    private String raza;
    @Column(name = "edad")
    private Integer edad;
    @Size(max = 255)
    @Column(name = "foto")
    private String foto;
    @OneToMany(mappedBy = "idMascota")
    private Collection<Solicitudadopcion> solicitudadopcionCollection;
    @JoinColumn(name = "email_refugio", referencedColumnName = "email")
    @ManyToOne
    private Refugio emailRefugio;

    public Mascota() {
    }

    public Mascota(Integer idMascota) {
        this.idMascota = idMascota;
    }

    public Mascota(Integer idMascota, String nombre, String especie) {
        this.idMascota = idMascota;
        this.nombre = nombre;
        this.especie = especie;
    }

    public Integer getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(Integer idMascota) {
        this.idMascota = idMascota;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @XmlTransient
    public Collection<Solicitudadopcion> getSolicitudadopcionCollection() {
        return solicitudadopcionCollection;
    }

    public void setSolicitudadopcionCollection(Collection<Solicitudadopcion> solicitudadopcionCollection) {
        this.solicitudadopcionCollection = solicitudadopcionCollection;
    }

    public Refugio getEmailRefugio() {
        return emailRefugio;
    }

    public void setEmailRefugio(Refugio emailRefugio) {
        this.emailRefugio = emailRefugio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMascota != null ? idMascota.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mascota)) {
            return false;
        }
        Mascota other = (Mascota) object;
        if ((this.idMascota == null && other.idMascota != null) || (this.idMascota != null && !this.idMascota.equals(other.idMascota))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.uva.petadopt.model.Mascota[ idMascota=" + idMascota + " ]";
    }
    
}
