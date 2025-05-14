
package es.uva.petadopt.dto;

import es.uva.petadopt.model.Cliente;
import es.uva.petadopt.model.Mascota;


public class SolicitudDTO {

    private Cliente cliente;
    private Mascota mascota;

    // Getters y setters
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }
}
