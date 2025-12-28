package es.severo.ud4.service;

import es.severo.ud4.entities.DiaSemana;
import es.severo.ud4.entities.Grupo;
import es.severo.ud4.repository.GrupoRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GrupoService {

    private final GrupoRepository grupoRepository;

    public GrupoService(GrupoRepository grupoRepository) {
        this.grupoRepository = grupoRepository;
    }

    // Obtener todos los grupos paginados
    public Page<Grupo> findAll(Pageable pageable) {
        return grupoRepository.findAll(pageable);
    }

    // Filtrar por día de la semana
    public List<Grupo> findByDiaSemana(DiaSemana diaSemana) {
        return grupoRepository.findByDiaSemana(diaSemana);
    }


    // JPQL: grupos por responsable
    public List<Grupo> buscarGruposPorResponsable(String dni) {
        return grupoRepository.buscarGruposPorResponsable(dni);
    }


    // CRUD
    public Optional<Grupo> findById(Long id) {
        return grupoRepository.findById(id);
    }

    public Grupo save(Grupo grupo) {
        return grupoRepository.save(grupo);
    }

    public void deleteById(Long id) {
        grupoRepository.deleteById(id);
    }
}
