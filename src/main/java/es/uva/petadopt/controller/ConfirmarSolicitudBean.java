package es.uva.petadopt.controller;

import es.uva.petadopt.client.MascotaRestClient;
import es.uva.petadopt.model.Mascota;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class ConfirmarSolicitudBean implements Serializable{
    private int mascotaId;
    private Mascota mascota;
    private final MascotaRestClient mc = new MascotaRestClient();
    
    public void cargarMascota(){
        mascota = mc.find(mascotaId);
    }
    
    public String getTime() {
        LocalDateTime ahora = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM 'de' yyyy, HH:mm", new Locale("es", "ES"));

        return ahora.format(formatter);
    }
    
    public String verSolicitudes() {
        return "/cliente/solicitudes.xhtml?faces-redirect=true";
    }

    public int getMascotaId() {
        return mascotaId;
    }

    public void setMascotaId(int mascotaId) {
        this.mascotaId = mascotaId;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }
    
}
