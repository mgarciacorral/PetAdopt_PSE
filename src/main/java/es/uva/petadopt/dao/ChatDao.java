/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.petadopt.dao;

import es.uva.petadopt.model.Chat;
import es.uva.petadopt.model.Cliente;
import es.uva.petadopt.model.Refugio;
import es.uva.petadopt.model.Solicitudadopcion;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ChatDao{
    
    @PersistenceContext(unitName = "PetAdoptPU")
    private EntityManager entityManager;
    
    
    public void createChat(Cliente cliente, Refugio refugio, Solicitudadopcion solicitud){
        
        Chat chat = new Chat();
        
        chat.setEmailCliente(cliente);
        chat.setEmailRefugio(refugio);
        chat.setIdSolicitud(solicitud);
        
        entityManager.persist(chat);
    }
    
}
