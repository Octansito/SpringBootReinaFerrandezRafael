package es.severo.ud4.repository;

import es.severo.ud4.entities.Animal;
import es.severo.ud4.entities.AnimalEstado;
import es.severo.ud4.entities.AnimalTipo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnimalRepository extends JpaRepository<Animal, Long> {

    //Filtro simple de buscar animales por estado
    List<Animal> findByEstado(AnimalEstado estado);

    // Relación Animal-->Adopcion
    List<Animal> findByAdopcionNombreAdoptante(String nombre);
    //Conteo por estado
    long contByEstado(AnimalEstado estado);
    Page<Animal> findAll (Pageable pageable);
    Page<Animal> findByEstado(AnimalEstado estado, Pageable pageable);
    // Ordenación
    Page<Animal> findByEstadoOrderByFechaIngresoDesc(AnimalEstado estado,Pageable pageable);

    // JPQL: selecciona de Animal los animales por tipo X
    @Query("SELECT a FROM Animal a WHERE a.tipo = :tipo")
    Page<Animal> buscarPorTipo(@Param("tipo") AnimalTipo tipo, Pageable pageable);

}
