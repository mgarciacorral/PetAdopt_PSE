
package es.uva.petadopt.controller;

import es.uva.petadopt.client.ChatRestClient;
import es.uva.petadopt.client.MensajeRestClient;
import es.uva.petadopt.model.Mensaje;
import java.io.IOException;
import java.io.Serializable;
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
    private ChatRestClient chatClient;

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        String idChatParam = context.getExternalContext().getRequestParameterMap().get("idChat");

        try {
            if (idChatParam != null) {
                idChat = Integer.parseInt(idChatParam);
                System.out.println("idChat recibido: " + idChat);
            } else {
                redirigirABuscar();
            }
        } catch (NumberFormatException e) {
            System.err.println("idChat no es un número válido.");
            redirigirABuscar();
        }

        mensajesRefugio = cargarMensajeRefugio();
        mensajesCliente = cargarMensajeCliente();
    }
        
    private void redirigirABuscar() {
        try {
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect("buscar.xhtml");
        } catch (IOException e) {

        }
    }

    public List<Mensaje> cargarMensajeRefugio() {
        String emailCliente = chatClient.getChatById(idChat).getEmailCliente();
        return mensajeClient.findMensajesRefugio(idChat, emailCliente);
    }

    public List<Mensaje> cargarMensajeCliente() {
        String emailRefugio = chatClient.getChatById(idChat).getEmailRefugio();
        return mensajeClient.findMensajesRefugio(idChat, emailRefugio);
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
