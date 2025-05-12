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
    private Mascota selectedMascota;
    private String filtroBusqueda;
    private List<Mascota> mascotas;
    private List<String> especies;
    private List<String> razas;


    
    @PostConstruct
    public void init(){
        buscarMascotas();
        cargarEspecies();
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
    
    public void buscarPorNombre(){
        if (filtroBusqueda == null || filtroBusqueda.isEmpty()) {
            // Si no hay filtro, mostrar todas las mascotas
            mascotas = mascotaDao.findAll();
        } else {
            // Si hay filtro, buscar mascotas por nombre
            mascotas = mascotaDao.buscarMascotasPorNombre(filtroBusqueda);

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

    public void verMascota(int id) {
        this.selectedMascota = mascotaDao.findById(id);
        System.out.println(selectedMascota.getNombre());

    }
    
    public String verPaginaBusqueda() {
        return "/cliente/buscar.xhtml?faces-redirect=true";
    }
    
    public String verSolicitudes() {
        return "/cliente/solicitudes.xhtml?faces-redirect=true";
    }
    
    public String verPerfil() {
        return "/cliente/perfil.xhtml?faces-redirect=true";
    }

    // Getters y setters
    public String getSelectedEspecie() {
        return selectedEspecie;
    }

    public void setSelectedEspecie(String selectedEspecie) {
        this.selectedEspecie = selectedEspecie;
    }
    
    public Mascota getSelectedMascota(){
        return selectedMascota;
    }
    
    public void setSelectedMascota(Mascota selectedMascota){
       this.selectedMascota = selectedMascota;
    }
    
    public String getFiltroBusqueda() {
        return filtroBusqueda;
    }

    public void setFiltroBusqueda(String filtroBusqueda) {
        this.filtroBusqueda = filtroBusqueda;
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
    
    public List<String> getEspecies() {
        return especies;
    }

    public void setEspecies(List<String> especies) {
        this.especies = especies;
    }
    
    public List<String> getRazas(){
        return razas;
    }
    
    public void setRazas(List<String> razas){
        this.razas = razas;
    }
}
