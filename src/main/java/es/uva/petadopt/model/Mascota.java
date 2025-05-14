package es.uva.petadopt.model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "mascota")
@NamedQueries({
    @NamedQuery(name = "Mascota.findAll", query = "SELECT m FROM Mascota m"),
    @NamedQuery(name = "Mascota.findByIdMascota", query = "SELECT m FROM Mascota m WHERE m.idMascota = :idMascota"),
    @NamedQuery(name = "Mascota.findByRefugio", query = "SELECT m FROM Mascota m WHERE LOWER(m.refugio) LIKE :refugio")
})
public class Mascota implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mascota")
    private Integer idMascota;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "especie", nullable = false, length = 50)
    private String especie;

    @Column(name = "raza", nullable = false, length = 50)
    private String raza;

    @Column(name = "edad", nullable = false)
    private Integer edad;

    @Column(name = "coste", nullable = false)
    private Integer coste;

    @Column(name = "refugio", nullable = false, length = 100)
    private String refugio;

    @Lob
    @Column(name = "foto")
    private byte[] foto;

    // === Getters y Setters ===

    public Integer getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(Integer idMascota) {
        this.idMascota = idMascota;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Integer getCoste() {
        return coste;
    }

    public void setCoste(Integer coste) {
        this.coste = coste;
    }

    public String getRefugio() {
        return refugio;
    }

    public void setRefugio(String refugio) {
        this.refugio = refugio;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    @Override
    public int hashCode() {
        return (idMascota != null ? idMascota.hashCode() : 0);
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Mascota)) {
            return false;
        }
        Mascota other = (Mascota) object;
        return (this.idMascota != null || other.idMascota == null) &&
               (this.idMascota == null || this.idMascota.equals(other.idMascota));
    }

    @Override
    public String toString() {
        return "es.uva.petadopt.model.Mascota[ idMascota=" + idMascota + " ]";
    }
}
