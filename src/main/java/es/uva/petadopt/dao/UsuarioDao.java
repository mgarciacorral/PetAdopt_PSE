package es.uva.petadopt.dao;

import es.uva.petadopt.model.Usuario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.security.crypto.bcrypt.BCrypt;
import static org.springframework.security.crypto.bcrypt.BCrypt.gensalt;

@Stateless
public class UsuarioDao {
    
    @PersistenceContext(unitName = "PetAdoptPU")
    private EntityManager entityManager;

    public Usuario findByEmail(String email) {
        return entityManager.find(Usuario.class, email);
    }


    // Método para comprobar si la contraseña ingresada coincide con la guardada
    public boolean checkPassword(String rawPassword, String hashedPassword) {
        return BCrypt.checkpw(rawPassword, hashedPassword);
    }

    public void save(Usuario usuario) {
        // Ciframos la contraseña antes de guardarla
        String hashedPassword = BCrypt.hashpw(usuario.getPassword(), gensalt());
        usuario.setPassword(hashedPassword);
        entityManager.persist(usuario);
    }
}
