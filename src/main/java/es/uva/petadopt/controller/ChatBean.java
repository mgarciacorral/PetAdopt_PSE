
package es.uva.petadopt.controller;

import es.uva.petadopt.model.Chat;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class ChatBean implements Serializable{
    
    
    private List<Chat> chats;
    
    
    @PostConstruct
    public void init(){
    
    }
    
}
