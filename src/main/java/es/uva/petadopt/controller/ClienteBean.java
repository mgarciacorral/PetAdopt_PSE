package es.uva.petadopt.controller;

import es.uva.petadopt.model.Mascota;
import es.uva.petadopt.dao.MascotaDao;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class ClienteBean implements Serializable {

    @Inject
    private MascotaDao mascotaDao;

    private String selectedEspecie;
    private String selectedRaza;
    private List<Mascota> mascotas;
    private List<String> especies;
    private List<String> razas;

    
    @PostConstruct
    public void init(){
        buscarMascotas();
        cargarEspecies();
        System.out.println(especies);
    }
    
    // Método para buscar las mascotas
    public void buscarMascotas() {
        // Realiza la búsqueda con los filtros seleccionados
        if (selectedEspecie != null && !selectedEspecie.isEmpty()) {
            mascotas = mascotaDao.findByEspecie(selectedEspecie);
        }
        if (selectedRaza != null && !selectedRaza.isEmpty()) {
            mascotas = mascotaDao.findByRaza(selectedRaza);
        }

        // Si no se seleccionan filtros, mostramos todas las mascotas
        if ((selectedEspecie == null || selectedEspecie.isEmpty()) && 
            (selectedRaza == null || selectedRaza.isEmpty())) {
            mascotas = mascotaDao.findAll();
        }
    }
    
    public void cargarEspecies(){
        this.especies = mascotaDao.obtenerEspecies();
    }
    
    public void cargarRazas(){
        if (selectedEspecie != null && !selectedEspecie.isEmpty()) {
            this.razas = mascotaDao.obtenerRazasPorEspecie(selectedEspecie); // Obtener razas según la especie
        }
    }

    // Método para ver el detalle de una mascota
    public String verMascota(int id) {
        // Redirige a la página de detalle de la mascota
        return "/cliente/detalleMascota.xhtml?faces-redirect=true&id=" + id;
    }

    // Getters y setters
    public String getSelectedEspecie() {
        return selectedEspecie;
    }

    public void setSelectedEspecie(String selectedEspecie) {
        this.selectedEspecie = selectedEspecie;
    }

    public String getSelectedRaza() {
        return selectedRaza;
    }

    public void setSelectedRaza(String selectedRaza) {
        this.selectedRaza = selectedRaza;
    }

    public List<Mascota> getMascotas() {
        return mascotas;
    }

    public void setMascotas(List<Mascota> mascotas) {
        this.mascotas = mascotas;
    }
}
