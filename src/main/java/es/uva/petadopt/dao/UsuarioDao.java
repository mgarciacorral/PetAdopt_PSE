package es.uva.petadopt.dao;

import es.uva.petadopt.model.Usuario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.security.crypto.bcrypt.BCrypt;

@Stateless
public class UsuarioDao {
    private final String fixedSalt = "$2a$12$myFixedSalt1234myFixedSalt1234";
    
    @PersistenceContext(unitName = "PetAdoptPU")
    private EntityManager entityManager;

    public Usuario findByEmail(String email) {
        return entityManager.find(Usuario.class, email);
    }


    // Método para comprobar si la contraseña ingresada coincide con la guardada
    public boolean checkPassword(String rawPassword, String hashedPassword) {
        String pw = BCrypt.hashpw(rawPassword, fixedSalt);
        return hashedPassword.equals(pw);
    }

    public void save(Usuario usuario) {
        // Ciframos la contraseña antes de guardarla
        String hashedPassword = BCrypt.hashpw(usuario.getPassword(), fixedSalt);
        usuario.setPassword(hashedPassword);
        entityManager.persist(usuario);
    }
}
