/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.petadopt.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andri
 */
@Entity
@Table(name = "solicitudadopcion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Solicitudadopcion.findAll", query = "SELECT s FROM Solicitudadopcion s"),
    @NamedQuery(name = "Solicitudadopcion.findByIdSolicitud", query = "SELECT s FROM Solicitudadopcion s WHERE s.idSolicitud = :idSolicitud"),
    @NamedQuery(name = "Solicitudadopcion.findByEmailCliente", query = "SELECT s FROM Solicitudadopcion s WHERE s.emailCliente = :emailCliente"),
    @NamedQuery(name = "Solicitudadopcion.findByIdMascota", query = "SELECT s FROM Solicitudadopcion s WHERE s.idMascota = :idMascota"),
    @NamedQuery(name = "Solicitudadopcion.findByEstado", query = "SELECT s FROM Solicitudadopcion s WHERE s.estado = :estado"),
    @NamedQuery(name = "Solicitudadopcion.findByFechaSolicitud", query = "SELECT s FROM Solicitudadopcion s WHERE s.fechaSolicitud = :fechaSolicitud")})
public class Solicitudadopcion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_solicitud")
    private Integer idSolicitud;
    @Size(max = 100)
    @Column(name = "email_cliente")
    private String emailCliente;
    @Column(name = "id_mascota")
    private Integer idMascota;
    @Size(max = 20)
    @Column(name = "estado")
    private String estado;
    @Column(name = "fecha_solicitud")
    @Temporal(TemporalType.DATE)
    private Date fechaSolicitud;

    public Solicitudadopcion() {
    }

    public Solicitudadopcion(Integer idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public Integer getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Integer idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }

    public Integer getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(Integer idMascota) {
        this.idMascota = idMascota;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSolicitud != null ? idSolicitud.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Solicitudadopcion)) {
            return false;
        }
        Solicitudadopcion other = (Solicitudadopcion) object;
        if ((this.idSolicitud == null && other.idSolicitud != null) || (this.idSolicitud != null && !this.idSolicitud.equals(other.idSolicitud))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.uva.petadopt.model.Solicitudadopcion[ idSolicitud=" + idSolicitud + " ]";
    }
    
}
