package es.severo.ud4.service;



import es.severo.ud4.entities.Animal;
import es.severo.ud4.entities.AnimalEstado;
import es.severo.ud4.entities.AnimalTipo;
import es.severo.ud4.repository.AnimalRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnimalService {

    private AnimalRepository animalRepository;

    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    // Obtener todos paginados
    public Page<Animal> findAll(Pageable pageable) {
        return animalRepository.findAll(pageable);
    }

//    // Filtro por estado (paginado)
//    public Page<Animal> findByEstado(AnimalEstado estado, Pageable pageable) {
//        return animalRepository.findByEstado(estado, pageable);
//    }

    // NamedQuery: buscar por tipo
    public Page<Animal> findByTipo(AnimalTipo tipo, Pageable pageable) {
        return animalRepository.findByTipo(tipo, pageable);
    }
    // Filtro por estado (NO paginado)
    public List<Animal> findByEstado(AnimalEstado estado) {
        return animalRepository.findByEstado(estado);
    }

    // Filtro por estado y ordenación por fechaIngreso DESC
    public Page<Animal> findByEstadoOrderByFechaIngresoDesc(
            AnimalEstado estado,
            Pageable pageable
    ) {
        return animalRepository.findByEstadoOrderByFechaIngresoDesc(estado, pageable);
    }


    // Relación Animal --> Adopción
    public List<Animal> findByAdoptante(String nombreAdoptante) {
        return animalRepository.findByAdopcionNombreAdoptante(nombreAdoptante);
    }

    // Conteo por estado
    public long countByEstado(AnimalEstado estado) {
        return animalRepository.countByEstado(estado);
    }

    // CRUD
    public Optional<Animal> findById(Long id) {
        return animalRepository.findById(id);
    }

    public Animal save(Animal animal) {
        return animalRepository.save(animal);
    }

    public void deleteById(Long id) {
        animalRepository.deleteById(id);
    }
}
