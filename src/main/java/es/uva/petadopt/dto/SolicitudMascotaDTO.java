
package es.uva.petadopt.dto;

import es.uva.petadopt.model.Mascota;
import es.uva.petadopt.model.Solicitudadopcion;


public class SolicitudMascotaDTO {

    private Solicitudadopcion solicitud;
    private Mascota mascota;

    public SolicitudMascotaDTO(Solicitudadopcion solicitud, Mascota mascota) {
        this.solicitud = solicitud;
        this.mascota = mascota;
    }

    public Solicitudadopcion getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Solicitudadopcion solicitud) {
        this.solicitud = solicitud;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }
}
