package es.severo.ud4.repository;

import es.severo.ud4.entities.Adopcion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AdopcionRepository extends JpaRepository<Adopcion, Long> {
//
//    //Filtro búsqueda adopcion por nombre del adoptante
//    List<Adopcion> findByNombreAdoptanteContainingIgnoreCase(String nombre);

    //Busqueda adopciones entre fechas
    Page<Adopcion> findByFechaAdopcionBetween(LocalDate inicio, LocalDate fin, Pageable pageable);

    //Ordenacion de fechas
    Page<Adopcion> findAllByOrderByFechaAdopcionDesc(Pageable pageable);
    // JPQL: buscar adopciones por nombre del adoptante
    @Query("SELECT a FROM Adopcion a WHERE a.nombreAdoptante = :nombre ORDER BY a.fechaAdopcion DESC")
    Page<Adopcion> buscarPorNombreAdoptante(@Param("nombre") String nombre, Pageable pageable);


}
