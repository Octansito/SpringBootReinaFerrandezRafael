package es.severo.ud4.repository;
import es.severo.ud4.entities.VoluntarioRol;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import es.severo.ud4.entities.Voluntario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VoluntarioRepository extends JpaRepository<Voluntario, String> {
    //Filtrar por rol de voluntario
    List<Voluntario> findByRol(VoluntarioRol rol);

    // Relación Voluntario-->Animal (ManyToMany)
    List<Voluntario> findByAnimalesNombre(String nombreAnimal);

    //Buscar todos los voluntarios
    Page<Voluntario> findAll(Pageable pageable);

    // JPQL: voluntarios que atienden a un animal
    @Query("SELECT FROM Voluntario v JOIN v.animales a WHERE a.nombre = :nombreAnimal")
    List<Voluntario> buscarVoluntariosPorAnimal(@Param("nombreAnimal") String nombreAnimal);
}
