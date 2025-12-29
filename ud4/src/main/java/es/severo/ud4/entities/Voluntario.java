package es.severo.ud4.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(name="dni", length = 9)
    private String dni;

    @Column(name="nombre", nullable = false)
    private String nombre;

    @Column(name="telefono", nullable = false)
    private String telefono;

    @Enumerated(EnumType.STRING)
    @Column(name="rol", nullable = false)
    private VoluntarioRol rol;

    @Column(name="antiguedad", nullable = false)
    private Integer antiguedad;

    @ManyToMany
    @JoinTable(
            name = "voluntario_animal",
            joinColumns = @JoinColumn(name = "dni_voluntario"),
            inverseJoinColumns = @JoinColumn(name = "id_animal")
    )
    @JsonIgnore
    private Set<Animal> animales;

    @ManyToMany(mappedBy = "voluntarios")
    @JsonIgnore
    private Set<Grupo> grupos;
}
