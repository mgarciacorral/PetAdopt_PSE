package es.uva.petadopt.controller;

import es.uva.petadopt.client.RefugioRestClient;
import es.uva.petadopt.client.UsuarioRestClient;
import es.uva.petadopt.model.Refugio;
import es.uva.petadopt.model.Usuario;
import java.io.Serializable;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class AdminBean implements Serializable{
    private List<Usuario> usuarios;
    private final UsuarioRestClient uc = new UsuarioRestClient();
    
    @PostConstruct
    public void init() {
        usuarios = uc.findAll();
        System.out.println(usuarios);
    }
    
    public String verPanel(){
        return "/admin/panel.xhtml?faces-redirect=true";
    }  
    
    public boolean isAutorizado(Usuario usuario){
        if(usuario.getTipo().equals("cliente") || usuario.getTipo().equals("admin")){
            return TRUE;
        }else{
            RefugioRestClient rc = new RefugioRestClient();
            return rc.findByEmail(usuario.getEmail()).getAutorizado();
        }
    }
    
    public boolean isAdmin(Usuario user){
        if(user.getTipo().equals("admin")){
            return TRUE;
        }else{
            return FALSE;
        }
    }
    
    public void eliminar(Usuario user){
        new UsuarioRestClient().delete(user);
    }
    
    public void autorizar(String email){
        RefugioRestClient rc = new RefugioRestClient();
        Refugio refugio = rc.findByEmail(email);
        refugio.setAutorizado(TRUE);
        rc.update(refugio);
    }
    
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
