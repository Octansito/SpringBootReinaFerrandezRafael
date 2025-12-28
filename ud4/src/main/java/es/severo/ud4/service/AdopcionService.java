package es.severo.ud4.service;


import es.severo.ud4.entities.Adopcion;
import es.severo.ud4.repository.AdopcionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AdopcionService {

    private AdopcionRepository adopcionRepository;

    public AdopcionService(AdopcionRepository adopcionRepository) {
        this.adopcionRepository = adopcionRepository;
    }

    // Obtener todas las adopciones paginadas
    public Page<Adopcion> findAll(Pageable pageable) {
        return adopcionRepository.findAll(pageable);
    }

    // Buscar adopciones por nombre del adoptante (JPQL)
    public Page<Adopcion> findByNombreAdoptante(String nombre, Pageable pageable) {
        return adopcionRepository.buscarPorNombreAdoptante(nombre, pageable);
    }

    // Buscar adopciones entre fechas
    public Page<Adopcion> findByFechaBetween(
            LocalDate inicio,
            LocalDate fin,
            Pageable pageable
    ) {
        return adopcionRepository.findByFechaAdopcionBetween(inicio, fin, pageable);
    }
    // Obtener adopciones ordenadas por fecha descendente
    public Page<Adopcion> findAllOrderByFechaDesc(Pageable pageable) {
        return adopcionRepository.findAllByOrderByFechaAdopcionDesc(pageable);
    }


//    // Buscar adopciones por nombre del adoptante (no paginado)
//    public List<Adopcion> findByNombre(String nombre) {
//        return adopcionRepository.findByNombreAdoptanteContainingIgnoreCase(nombre);
//    }

    // CRUD
    public Optional<Adopcion> findById(Long id) {
        return adopcionRepository.findById(id);
    }

    public Adopcion save(Adopcion adopcion) {
        return adopcionRepository.save(adopcion);
    }

    public void deleteById(Long id) {
        adopcionRepository.deleteById(id);
    }
}
