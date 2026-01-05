package es.severo.ud4.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@NamedQuery(
        name = "Animal.findByTipoNamed",
        query = "SELECT a FROM Animal a WHERE a.tipo = :tipo"
)
@Table(name = "animal")
@Getter
@Setter
@NoArgsConstructor

public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_animal")
    private Long id;

    @Column(name="nombre", nullable = false)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(name="tipo", nullable = false)
    private AnimalTipo tipo;

    @Column(name="edad", nullable = false)
    private Integer edad;

    @Enumerated(EnumType.STRING)
    @Column(name="estado", nullable = false)
    private AnimalEstado estado;

    @Column(name = "fecha_ingreso")
    private LocalDate fechaIngreso;


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id_adopcion")
    private Adopcion adopcion;

    @ManyToMany(mappedBy = "animales")
    private Set<Voluntario> voluntarios;
}
