
package es.uva.petadopt.dto;


public class ChatDTO {
    
    
    private String clienteId;
    private String refugioId;
    private int solicitudId;

    public String getClienteId() {
        return clienteId;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public String getRefugioId() {
        return refugioId;
    }

    public void setRefugioId(String refugioId) {
        this.refugioId = refugioId;
    }

    public int getSolicitudId() {
        return solicitudId;
    }

    public void setSolicitudId(int solicitudId) {
        this.solicitudId = solicitudId;
    }
}

