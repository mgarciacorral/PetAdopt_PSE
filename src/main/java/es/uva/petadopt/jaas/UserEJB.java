package es.uva.petadopt.jaas;

import es.uva.petadopt.model.Cliente;
import es.uva.petadopt.model.Refugio;
import es.uva.petadopt.model.UserGroups;
import es.uva.petadopt.model.Usuario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;


@Stateless
public class UserEJB {

    @PersistenceContext
    private EntityManager em;

    public Usuario createCliente(Usuario user, Cliente cliente) {
        try {
            user.setPassword(AuthenticationUtils.encodeSHA256(user.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        UserGroups group = new UserGroups();
        group.setEmail(user.getEmail());
        group.setGrupo("cliente");
        em.persist(user);
        em.persist(group);
        em.persist(cliente);
        return user;
    }
    
    public Usuario createRefugio(Usuario user, Refugio refugio) {
        try {
            user.setPassword(AuthenticationUtils.encodeSHA256(user.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        UserGroups group = new UserGroups();
        group.setEmail(user.getEmail());
        group.setGrupo("refugio");
        em.persist(user);
        em.persist(group);
        em.persist(refugio);
        return user;
    
    }
    
    public Usuario findByEmail(String email) {
        TypedQuery<Usuario> query = em.createNamedQuery("Usuario.findByEmail", Usuario.class);
        query.setParameter("email", email);
        Usuario user = null;
        try {
            user = query.getSingleResult();
        } catch (Exception e) {
        }
        return user;
    }
}
