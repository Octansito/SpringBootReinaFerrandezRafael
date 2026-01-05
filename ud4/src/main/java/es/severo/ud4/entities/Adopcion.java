package es.severo.ud4.entities;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "adopcion")
@Getter
@Setter
@NoArgsConstructor
public class Adopcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_adopcion")
    private Long id;

    @Column(name = "nombre_adoptante", nullable = false)
    private String nombreAdoptante;

    @Column(name="telefono")
    private String telefono;

    @Column(name = "fecha_adopcion", nullable = false)
    private LocalDate fechaAdopcion;

    @Column(name="direccion", nullable = false)
    private String direccion;

    @JsonManagedReference
    @OneToMany(mappedBy = "adopcion", cascade = CascadeType.PERSIST)
    private Set<Animal> animales;


}
