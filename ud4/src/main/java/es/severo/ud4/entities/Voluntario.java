package es.severo.ud4.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "voluntario")
@Getter
@Setter
@NoArgsConstructor

public class Voluntario {
    @Id
    @Column(length = 9)
    private String dni;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String telefono;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VoluntarioRol rol;

    @Column(nullable = false)
    private int antiguedad;

    @ManyToMany
    @JoinTable(
            name = "voluntario_animal",
            joinColumns = @JoinColumn(name = "dni_voluntario"),
            inverseJoinColumns = @JoinColumn(name = "id_animal")
    )
    private Set<Animal> animales;

    @ManyToMany(mappedBy = "voluntarios")
    private Set<Grupo> grupos;
}
