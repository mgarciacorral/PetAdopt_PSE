
package es.uva.petadopt.controller;

import es.uva.petadopt.client.ChatRestClient;
import es.uva.petadopt.client.MensajeRestClient;
import es.uva.petadopt.model.Cliente;
import es.uva.petadopt.model.Mensaje;
import es.uva.petadopt.model.Usuario;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;


@Named
@ViewScoped
public class ChatBean implements Serializable{
    
    private final MensajeRestClient mensajeClient = new MensajeRestClient();
    
    private int idChat;

    private List<Mensaje> mensajesRefugio;
    private List<Mensaje> mensajesCliente;
    private List<Mensaje> mensajes;
    private ChatRestClient chatClient;
    
    private Usuario usuario;
    
    private String contenidoMensaje;

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context == null) {
            System.err.println("FacesContext es null en init(), probablemente no se está ejecutando en ciclo JSF");
            return;  // Salimos para evitar errores
        }

        String idChatParam = context.getExternalContext().getRequestParameterMap().get("idChat");
        usuario = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogueado");

        try {
            if (idChatParam != null) {
                idChat = Integer.parseInt(idChatParam);
            } else {
                redirigirABuscar();
            }
        } catch (NumberFormatException e) {
            System.err.println("idChat no es un número válido.");
            redirigirABuscar();
        } catch (Exception e) {
            System.err.println("Error en redirigirABuscar: " + e.getMessage());
        }

        try {
            mensajesRefugio = cargarMensajeRefugio();
            mensajesCliente = cargarMensajeCliente();
        } catch (Exception e) {
            System.err.println("Error cargando mensajes: " + e.getMessage());
        }
    }

        
    public void redirigirABuscar() {
        try {
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect("buscar.xhtml");
        } catch (IOException e) {

        }
    }
    
    public void guardarMensaje(){
        System.out.println("guardao");
        
        Mensaje mensaje = new Mensaje();
        mensaje.setContenido(usuario.getEmail());
        mensaje.setRemitente(contenidoMensaje);
        mensaje.setFechaEnvio(Date.valueOf(LocalDate.now()));
        
        mensajeClient.create(mensaje);
    }
    
    public void cargarMensajes(){
        mensajes = mensajeClient.findMensajesByChat(idChat);
    }
    

    public List<Mensaje> cargarMensajeRefugio() {
        String emailCliente = chatClient.getChatById(idChat).getEmailCliente();
        return mensajeClient.findMensajesRefugio(idChat, emailCliente);
    }

    public List<Mensaje> cargarMensajeCliente() {
        String emailRefugio = chatClient.getChatById(idChat).getEmailRefugio();
        return mensajeClient.findMensajesRefugio(idChat, emailRefugio);
    }

    public List<Mensaje> getMensajesRefugio() {
        return mensajesRefugio;
    }

    public void setMensajesRefugio(List<Mensaje> mensajesRefugio) {
        this.mensajesRefugio = mensajesRefugio;
    }

    public String getContenidoMensaje() {
        return contenidoMensaje;
    }

    public void setContenidoMensaje(String contenidoMensaje) {
        this.contenidoMensaje = contenidoMensaje;
    }


    public List<Mensaje> getMensajesCliente() {
        return mensajesCliente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public void setMensajesCliente(List<Mensaje> mensajesCliente) {
        this.mensajesCliente = mensajesCliente;
    }

    public List<Mensaje> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }
    
    

    public ChatRestClient getChatClient() {
        return chatClient;
    }

    public void setChatClient(ChatRestClient chatClient) {
        this.chatClient = chatClient;
    }

    public List<Mensaje> getMensajeRefugio() {
        return mensajesRefugio;
    }

    public void setMensajeRefugio(List<Mensaje> mensajeRefugio) {
        this.mensajesRefugio = mensajeRefugio;
    }

    public List<Mensaje> getMensajeCliente() {
        return mensajesCliente;
    }

    public void setMensajeCliente(List<Mensaje> mensajeCliente) {
        this.mensajesCliente = mensajeCliente;
    }

    public int getIdChat() {
        return idChat;
    }

    public void setIdChat(int idChat) {
        this.idChat = idChat;
    }


    
}
