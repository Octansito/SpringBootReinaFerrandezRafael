package es.severo.ud4.controller;

import es.severo.ud4.dto.AdopcionDTO;
import es.severo.ud4.dto.AnimalDTO;
import es.severo.ud4.entities.Adopcion;
import es.severo.ud4.service.AdopcionService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/adopciones")
public class AdopcionController {

    private final AdopcionService adopcionService;

    public AdopcionController(AdopcionService adopcionService) {
        this.adopcionService = adopcionService;
    }

    /**
     * GET ALL (paginado)
     * /api/adopciones
     */
    @GetMapping
    public ResponseEntity<Page<Adopcion>> findAll(
            @PageableDefault(size = 10) Pageable pageable
    ) {

        Page<Adopcion> page = adopcionService.findAll(pageable);
        return ResponseEntity.ok(page);
    }

    /**
     * GET por nombre adoptante
     * /api/adopciones/nombre/{nombre}
     */
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Page<Adopcion>> findByNombre(
            @PathVariable String nombre,
            @PageableDefault(size = 10) Pageable pageable
    ) {

        Page<Adopcion> page = adopcionService.findByNombreAdoptante(nombre, pageable);
        return ResponseEntity.ok(page);
    }

    /**
     * GET ordenado por fecha descendente
     * /api/adopciones/fecha
     */
    @GetMapping("/fecha")
    public ResponseEntity<Page<Adopcion>> findAllOrderByFecha(
            @PageableDefault(size = 10) Pageable pageable
    ) {

        Page<Adopcion> page = adopcionService.findAllOrderByFechaDesc(pageable);
        return ResponseEntity.ok(page);
    }


    /**
     * GET por rango de fechas
     * /api/adopciones/fechas_inicio=2024-01-01 y fin=2024-12-31
     */
    @GetMapping("/fechas")
    public ResponseEntity<Page<Adopcion>> findByFechas(
            @RequestParam LocalDate inicio,
            @RequestParam LocalDate fin,
            @PageableDefault(size = 10) Pageable pageable
    ) {

        Page<Adopcion> page = adopcionService.findByFechaBetween(inicio, fin, pageable);
        return ResponseEntity.ok(page);
    }
//    /**
//     * GET por nombre adoptante (no paginado)
//     * /api/adopciones/nombre/{nombre}
//     */
//    @GetMapping("/nombre/{nombre}")
//    public ResponseEntity<List<Adopcion>> findByNombreLista(
//            @PathVariable String nombre
//    ) {
//
//        List<Adopcion> lista = adopcionService.findByNombre(nombre);
//        return ResponseEntity.ok(lista);
//    }
    /**
     * GET animales de una adopción
     * /api/adopciones/{id}/animales
     */
    @GetMapping("/{id}/animales")
    public ResponseEntity<List<AnimalDTO>> findAnimalesByAdopcion(
            @PathVariable Long id
    ) {
        Optional<Adopcion> adopcionOpt = adopcionService.findById(id);

        if (adopcionOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<AnimalDTO> lista = adopcionOpt.get().getAnimales()
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
     * Obtiene 1 por id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Adopcion> findById(@PathVariable Long id) {

        Optional<Adopcion> adopcionOpt = adopcionService.findById(id);

        if (adopcionOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(adopcionOpt.get());
    }

    /**
     * Crea
     */
    @PostMapping
    public ResponseEntity<Adopcion> create(@RequestBody Adopcion adopcion) {

        Adopcion guardada = adopcionService.save(adopcion);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
    }

    /**
     * actualiza
     */
    @PutMapping("/{id}")
    public ResponseEntity<Adopcion> update(
            @PathVariable Long id,
            @RequestBody Adopcion adopcion
    ) {

        Optional<Adopcion> adopcionOpt = adopcionService.findById(id);

        if (adopcionOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        adopcion.setId(id);
        Adopcion actualizada = adopcionService.save(adopcion);

        return ResponseEntity.ok(actualizada);
    }

    /**
     *Actualización parcial (dirección)
     * /api/adopciones/{id}
     */
    @PatchMapping("/{id}")
    public ResponseEntity<AdopcionDTO> updatePartial(
            @PathVariable Long id,
            @RequestBody AdopcionDTO dto
    ) {
        Optional<Adopcion> adopcionOpt = adopcionService.findById(id);

        if (adopcionOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Adopcion adopcion = adopcionOpt.get();

        // SOLO campo modificable
        adopcion.setDireccion(dto.direccion());

        Adopcion actualizada = adopcionService.save(adopcion);

        AdopcionDTO dtoActualizado = new AdopcionDTO(
                actualizada.getId(),
                actualizada.getNombreAdoptante(),
                actualizada.getFechaAdopcion(),
                actualizada.getDireccion()
        );

        return ResponseEntity.ok(dtoActualizado);
    }



    /**
     * DELETE
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        if (adopcionService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        adopcionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
