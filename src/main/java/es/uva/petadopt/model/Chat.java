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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mgarc
 */
@Entity
@Table(name = "chat")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Chat.findAll", query = "SELECT c FROM Chat c"),
    @NamedQuery(name = "Chat.findByIdChat", query = "SELECT c FROM Chat c WHERE c.idChat = :idChat")})
public class Chat implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_chat")
    private Integer idChat;
    @JoinColumn(name = "email_cliente", referencedColumnName = "email")
    @ManyToOne
    private Cliente emailCliente;
    @JoinColumn(name = "email_refugio", referencedColumnName = "email")
    @ManyToOne
    private Refugio emailRefugio;
    @JoinColumn(name = "id_solicitud", referencedColumnName = "id_solicitud")
    @ManyToOne
    private Solicitudadopcion idSolicitud;
    @OneToMany(mappedBy = "idChat")
    private Collection<Mensaje> mensajeCollection;

    public Chat() {
    }

    public Chat(Integer idChat) {
        this.idChat = idChat;
    }

    public Integer getIdChat() {
        return idChat;
    }

    public void setIdChat(Integer idChat) {
        this.idChat = idChat;
    }

    public Cliente getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(Cliente emailCliente) {
        this.emailCliente = emailCliente;
    }

    public Refugio getEmailRefugio() {
        return emailRefugio;
    }

    public void setEmailRefugio(Refugio emailRefugio) {
        this.emailRefugio = emailRefugio;
    }

    public Solicitudadopcion getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Solicitudadopcion idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    @XmlTransient
    public Collection<Mensaje> getMensajeCollection() {
        return mensajeCollection;
    }

    public void setMensajeCollection(Collection<Mensaje> mensajeCollection) {
        this.mensajeCollection = mensajeCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idChat != null ? idChat.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Chat)) {
            return false;
        }
        Chat other = (Chat) object;
        if ((this.idChat == null && other.idChat != null) || (this.idChat != null && !this.idChat.equals(other.idChat))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.uva.petadopt.model.Chat[ idChat=" + idChat + " ]";
    }
    
}
