
package es.uva.petadopt.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "chat")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Chat.findAll", query = "SELECT c FROM Chat c"),
    @NamedQuery(name = "Chat.findByIdChat", query = "SELECT c FROM Chat c WHERE c.idChat = :idChat"),
    @NamedQuery(name = "Chat.findByEmailCliente", query = "SELECT c FROM Chat c WHERE c.emailCliente = :emailCliente"),
    @NamedQuery(name = "Chat.findByEmailRefugio", query = "SELECT c FROM Chat c WHERE c.emailRefugio = :emailRefugio"),
    @NamedQuery(name = "Chat.findByIdSolicitud", query = "SELECT c FROM Chat c WHERE c.idSolicitud = :idSolicitud")})
public class Chat implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_chat")
    private Integer idChat;
    @Size(max = 100)
    @Column(name = "email_cliente")
    private String emailCliente;
    @Size(max = 100)
    @Column(name = "email_refugio")
    private String emailRefugio;
    @Column(name = "id_solicitud")
    private Integer idSolicitud;

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

    public String getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }

    public String getEmailRefugio() {
        return emailRefugio;
    }

    public void setEmailRefugio(String emailRefugio) {
        this.emailRefugio = emailRefugio;
    }

    public Integer getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Integer idSolicitud) {
        this.idSolicitud = idSolicitud;
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
