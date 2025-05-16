package es.uva.petadopt.flow;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.flow.Flow;
import javax.faces.flow.builder.FlowBuilder;
import javax.faces.flow.builder.FlowBuilderParameter;
import javax.faces.flow.builder.FlowDefinition;

@ApplicationScoped
public class FlowConfig {

    @Produces
    @FlowDefinition
    public Flow defineAgregarMascotaFlow(@FlowBuilderParameter FlowBuilder flowBuilder) {
        final String flowId = "agregarMascota";

        flowBuilder.id("", flowId);

        // Estados del flujo
        flowBuilder.viewNode("datos", "/agregarMascota/datos.xhtml").markAsStartNode();
        flowBuilder.viewNode("imagen", "/agregarMascota/imagen.xhtml");
        flowBuilder.viewNode("confirmacion", "/agregarMascota/confirmacion.xhtml");

        flowBuilder.returnNode("salir")
                .fromOutcome("/refugio/mascotas.xhtml?faces-redirect=true");


        return flowBuilder.getFlow();
    }
}
