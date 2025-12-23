package es.severo.ud4.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "grupo")
@Getter
@Setter
@NoArgsConstructor
public class Grupo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_grupo")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "dia_semana", nullable = false)
    private DiaSemana diaSemana;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Turno turno;

    @ManyToOne
    @JoinColumn(name = "responsable")
    private Voluntario responsable;

    @ManyToMany
    @JoinTable(
            name = "voluntario_grupo",
            joinColumns = @JoinColumn(name = "id_grupo"),
            inverseJoinColumns = @JoinColumn(name = "dni_voluntario")
    )
    private Set<Voluntario> voluntarios;
}
