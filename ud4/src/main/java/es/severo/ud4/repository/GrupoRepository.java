package es.severo.ud4.repository;

import es.severo.ud4.entities.DiaSemana;
import es.severo.ud4.entities.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GrupoRepository extends JpaRepository<Grupo, Long> {
    //Filtrar por dia de la semana
    List<Grupo> findByDiaSemana(DiaSemana diaSemana);

    //Relación Grupo-->Voluntario (responsable)
    List<Grupo> findByResponsableDni(String dni);

    //Encontrar todos los grupos
    Page<Grupo> findAll(Pageable pageable);
    // JPQL: grupos por responsable
    @Query(" SELECT g FROM Grupo g WHERE g.responsable.dni = :dni")
    List<Grupo> buscarGruposPorResponsable(@Param("dni") String dni);
}
