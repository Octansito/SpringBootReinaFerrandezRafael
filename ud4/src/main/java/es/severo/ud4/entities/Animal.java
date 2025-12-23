package es.severo.ud4.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "animal")
@Getter
@Setter
@NoArgsConstructor

public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_animal")
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AnimalTipo tipo;

    @Column(nullable = false)
    private int edad;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AnimalEstado estado;

    @Column(name = "fecha_ingreso", nullable = false)
    private LocalDate fechaIngreso;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id_adopcion")
    private Adopcion adopcion;

    @ManyToMany(mappedBy = "animales")
    private Set<Voluntario> voluntarios;
}
