package es.severo.ud4.controller;

import es.severo.ud4.dto.AnimalDTO;
import es.severo.ud4.entities.Animal;
import es.severo.ud4.entities.AnimalEstado;
import es.severo.ud4.entities.AnimalTipo;
import es.severo.ud4.service.AnimalService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/animales")
public class AnimalController {


    private AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    /**
     * GET ALL + filtros (estado o tipo)
     * Ejemplo:
     * /api/animales
     * /api/animales?estado=Adoptado
     * /api/animales?tipo=Perro
     */
    @GetMapping
    public ResponseEntity<Page<AnimalDTO>> findAll(
            @RequestParam(required = false) AnimalTipo tipo
    ) {

        Pageable pageable = PageRequest.of(0, 10);

        Page<Animal> animales;

        if (tipo != null) {
            animales = animalService.findByTipo(tipo, pageable);
        } else {
            animales = animalService.findAll(pageable);
        }

        Page<AnimalDTO> animalesDTO = animales.map(animal ->
                new AnimalDTO(
                        animal.getId(),
                        animal.getNombre(),
                        animal.getTipo(),
                        animal.getEstado(),
                        animal.getEdad()
                )
        );

        return ResponseEntity.ok(animalesDTO);
    }

    /**
     * GET por estado (sin paginar)
     * /api/animales/estado/{estado}
     */
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<AnimalDTO>> findByEstado(
            @PathVariable AnimalEstado estado
    ) {

        List<AnimalDTO> lista = animalService.findByEstado(estado)
                .stream()
                .map(animal -> new AnimalDTO(
                        animal.getId(),
                        animal.getNombre(),
                        animal.getTipo(),
                        animal.getEstado(),
                        animal.getEdad()
                ))
                .toList();

        return ResponseEntity.ok(lista);
    }
    /**
     * GET por estado ordenado por fecha de ingreso
     * /api/animales/estado/{estado}/fecha
     */
    @GetMapping("/estado/{estado}/fecha")
    public ResponseEntity<Page<AnimalDTO>> findByEstadoOrderByFecha(
            @PathVariable AnimalEstado estado,
            @PageableDefault(size = 10) Pageable pageable
    ) {

        Page<AnimalDTO> page = animalService
                .findByEstadoOrderByFechaIngresoDesc(estado, pageable)
                .map(animal -> new AnimalDTO(
                        animal.getId(),
                        animal.getNombre(),
                        animal.getTipo(),
                        animal.getEstado(),
                        animal.getEdad()
                ));

        return ResponseEntity.ok(page);
    }
    /**
     * GET animales por nombre del adoptante
     * /api/animales/adoptante/{nombre}
     */
    @GetMapping("/adoptante/{nombre}")
    public ResponseEntity<List<AnimalDTO>> findByAdoptante(
            @PathVariable String nombre
    ) {
        List<AnimalDTO> lista = animalService.findByAdoptante(nombre)
                .stream()
                .map(animal -> new AnimalDTO(
                        animal.getId(),
                        animal.getNombre(),
                        animal.getTipo(),
                        animal.getEstado(),
                        animal.getEdad()
                ))
                .toList();

        return ResponseEntity.ok(lista);
    }
    /**
     * GET conteo de animales por estado
     * /api/animales/estado/{estado}/count
     */
    @GetMapping("/estado/{estado}/count")
    public ResponseEntity<Long> countByEstado(
            @PathVariable AnimalEstado estado
    ) {
        long total = animalService.countByEstado(estado);
        return ResponseEntity.ok(total);
    }


    /**
     * Obtiene1 por id
     */
    @GetMapping("/{id}")
    public ResponseEntity<AnimalDTO> findById(@PathVariable Long id) {
        Optional<Animal> optionalAnimal = animalService.findById(id);

        if (optionalAnimal.isPresent()) {
            Animal animal = optionalAnimal.get();
            AnimalDTO dto = new AnimalDTO(
                    animal.getId(),
                    animal.getNombre(),
                    animal.getTipo(),
                    animal.getEstado(),
                    animal.getEdad()
            );
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    /**
     * GET adopción de un animal
     * /api/animales/{id}/adopcion
     */
    @GetMapping("/{id}/adopcion")
    public ResponseEntity<Long> findAdopcionByAnimal(
            @PathVariable Long id
    ) {
        Optional<Animal> animalOpt = animalService.findById(id);

        if (animalOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Animal animal = animalOpt.get();

        if (animal.getAdopcion() == null) {
            return ResponseEntity.notFound().build();
        }

        // Devuelves el ID de la adopción
        return ResponseEntity.ok(animal.getAdopcion().getId());
    }


    /**
     * Crear animal
     */
    @PostMapping
    public ResponseEntity<AnimalDTO> create(@RequestBody AnimalDTO dto) {
        Animal animal = new Animal();
        animal.setNombre(dto.nombre());
        animal.setTipo(dto.tipo());
        animal.setEstado(dto.estado());
        animal.setEdad(dto.edad());

        Animal guardado = animalService.save(animal);

        AnimalDTO creado = new AnimalDTO(
                guardado.getId(),
                guardado.getNombre(),
                guardado.getTipo(),
                guardado.getEstado(),
                guardado.getEdad()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    /**
     * Actualiza animal completo
     */
    @PutMapping("/{id}")
    public ResponseEntity<AnimalDTO> update(
            @PathVariable Long id,
            @RequestBody AnimalDTO dto
    ) {
        Optional<Animal> optionalAnimal = animalService.findById(id);

        if (optionalAnimal.isPresent()) {
            Animal animal = optionalAnimal.get();
            animal.setNombre(dto.nombre());
            animal.setTipo(dto.tipo());
            animal.setEstado(dto.estado());
            animal.setEdad(dto.edad());

            Animal actualizado = animalService.save(animal);

            AnimalDTO dtoActualizado = new AnimalDTO(
                    actualizado.getId(),
                    actualizado.getNombre(),
                    actualizado.getTipo(),
                    actualizado.getEstado(),
                    actualizado.getEdad()
            );

            return ResponseEntity.ok(dtoActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     *  Actualización parcial (edad y estado)
     */
    @PatchMapping("/{id}")
    public ResponseEntity<AnimalDTO> updatePartial(
            @PathVariable Long id,
            @RequestBody AnimalDTO dto
    ) {
        Optional<Animal> optionalAnimal = animalService.findById(id);

        if (optionalAnimal.isPresent()) {
            Animal animal = optionalAnimal.get();

            animal.setEdad(dto.edad());
            animal.setEstado(dto.estado());

            Animal actualizado = animalService.save(animal);

            AnimalDTO dtoActualizado = new AnimalDTO(
                    actualizado.getId(),
                    actualizado.getNombre(),
                    actualizado.getTipo(),
                    actualizado.getEstado(),
                    actualizado.getEdad()
            );

            return ResponseEntity.ok(dtoActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * DELETE
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        if (animalService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        animalService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
