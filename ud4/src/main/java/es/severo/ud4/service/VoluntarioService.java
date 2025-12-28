package es.severo.ud4.service;

import es.severo.ud4.entities.Voluntario;
import es.severo.ud4.entities.VoluntarioRol;
import es.severo.ud4.repository.VoluntarioRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VoluntarioService {

    private final VoluntarioRepository voluntarioRepository;

    public VoluntarioService(VoluntarioRepository voluntarioRepository) {
        this.voluntarioRepository = voluntarioRepository;
    }

    // Obtener todos paginados
    public Page<Voluntario> findAll(Pageable pageable) {
        return voluntarioRepository.findAll(pageable);
    }

    // Filtrar por rol
    public List<Voluntario> findByRol(VoluntarioRol rol) {
        return voluntarioRepository.findByRol(rol);
    }


    // JPQL: voluntarios que atienden a un animal
    public List<Voluntario> buscarVoluntariosPorAnimal(String nombreAnimal) {
        return voluntarioRepository.buscarVoluntariosPorAnimal(nombreAnimal);
    }

    // CRUD
    public Optional<Voluntario> findById(String dni) {
        return voluntarioRepository.findById(dni);
    }

    public Voluntario save(Voluntario voluntario) {
        return voluntarioRepository.save(voluntario);
    }

    public void deleteById(String dni) {
        voluntarioRepository.deleteById(dni);
    }
}
