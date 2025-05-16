package es.uva.petadopt.controller;

import es.uva.petadopt.client.MascotaRestClient;
import es.uva.petadopt.model.Mascota;
import es.uva.petadopt.model.Refugio;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.flow.FlowScoped;
import javax.inject.Named;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;

@Named
@FlowScoped("agregarMascota")
public class AgregarMascotaFlowBean implements Serializable {

    private Mascota nuevaMascota = new Mascota();
    private UploadedFile imagen;
    private StreamedContent imagenPreview;
    private final MascotaRestClient mascotaRestClient = new MascotaRestClient();

    public String guardar() {
        Refugio refugio = (Refugio) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("refugioLogueado");

        nuevaMascota.setEmailRefugio(refugio.getEmail());
        mascotaRestClient.crearMascota(nuevaMascota);
        return "salir";
    }
    
    public String irAConfirmacion() {
        if (imagen != null) {
            String tipo = imagen.getContentType();
            if (!tipo.equals("image/jpeg") && !tipo.equals("image/png")) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Formato no permitido. Solo se aceptan JPG o PNG.", null));
                return null;
            }

            nuevaMascota.setFoto(imagen.getContent());
        }
        return "confirmacion"; // o el nombre de la vista del flow
    }
    
    public StreamedContent getFotoPreview() {
        if (imagen != null && imagen.getContent() != null) {
            imagenPreview = DefaultStreamedContent.builder()
                    .stream(() -> new ByteArrayInputStream(imagen.getContent()))
                    .contentType(imagen.getContentType())
                    .build();
        }
        
        return imagenPreview;
    }

    public void inicializar() {
        System.out.println("Flow inicializado");
    }

    public Mascota getNuevaMascota() {
        return nuevaMascota;
    }

    public void setNuevaMascota(Mascota nuevaMascota) {
        this.nuevaMascota = nuevaMascota;
    }

    public UploadedFile getImagen() {
        return imagen;
    }

    public void setImagen(UploadedFile imagen) {
        this.imagen = imagen;
    }
}
