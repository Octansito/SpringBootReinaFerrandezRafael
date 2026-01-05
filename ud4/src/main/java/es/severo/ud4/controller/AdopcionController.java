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
    public ResponseEntity<Page<AdopcionDTO>> findAll(
            @PageableDefault(size = 10) Pageable pageable
    ) {
        Page<AdopcionDTO> page = adopcionService.findAll(pageable)
                .map(a -> new AdopcionDTO(
                        a.getId(),
                        a.getNombreAdoptante(),
                        a.getFechaAdopcion(),
                        a.getDireccion()
                ));

        return ResponseEntity.ok(page);
    }

    /**
     * GET por nombre adoptante
     * /api/adopciones/nombre/{nombre}
     */
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Page<AdopcionDTO>> findByNombre(
            @PathVariable String nombre,
            @PageableDefault(size = 10) Pageable pageable
    ) {

        Page<AdopcionDTO> page = adopcionService
                .findByNombreAdoptante(nombre, pageable)
                .map(a -> new AdopcionDTO(
                        a.getId(),
                        a.getNombreAdoptante(),
                        a.getFechaAdopcion(),
                        a.getDireccion()
                ));

        return ResponseEntity.ok(page);
    }


    /**
     * GET ordenado por fecha descendente
     * /api/adopciones/fecha
     */
    @GetMapping("/fecha")
    public ResponseEntity<Page<AdopcionDTO>> findAllOrderByFecha(
            @PageableDefault(size = 10) Pageable pageable
    ) {

        Page<AdopcionDTO> page = adopcionService
                .findAllOrderByFechaDesc(pageable)
                .map(a -> new AdopcionDTO(
                        a.getId(),
                        a.getNombreAdoptante(),
                        a.getFechaAdopcion(),
                        a.getDireccion()
                ));

        return ResponseEntity.ok(page);
    }



    /**
     * GET por rango de fechas
     * /api/adopciones/fechas_inicio=2024-01-01 y fin=2024-12-31
     */
    @GetMapping("/fechas")
    public ResponseEntity<Page<AdopcionDTO>> findByFechas(
            @RequestParam LocalDate inicio,
            @RequestParam LocalDate fin,
            @PageableDefault(size = 10) Pageable pageable
    ) {

        Page<AdopcionDTO> page = adopcionService
                .findByFechaBetween(inicio, fin, pageable)
                .map(a -> new AdopcionDTO(
                        a.getId(),
                        a.getNombreAdoptante(),
                        a.getFechaAdopcion(),
                        a.getDireccion()
                ));

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
    public ResponseEntity<AdopcionDTO> findById(@PathVariable Long id) {

        Optional<Adopcion> adopcionOpt = adopcionService.findById(id);

        if (adopcionOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Adopcion a = adopcionOpt.get();

        return ResponseEntity.ok(
                new AdopcionDTO(
                        a.getId(),
                        a.getNombreAdoptante(),
                        a.getFechaAdopcion(),
                        a.getDireccion()
                )
        );
    }


    /**
     * Crea
     */
    @PostMapping
    public ResponseEntity<AdopcionDTO> create(@RequestBody AdopcionDTO dto) {

        Adopcion adopcion = new Adopcion();
        adopcion.setNombreAdoptante(dto.nombreAdoptante());
        adopcion.setFechaAdopcion(dto.fechaAdopcion());
        adopcion.setDireccion(dto.direccion());

        Adopcion guardada = adopcionService.save(adopcion);

        AdopcionDTO respuesta = new AdopcionDTO(
                guardada.getId(),
                guardada.getNombreAdoptante(),
                guardada.getFechaAdopcion(),
                guardada.getDireccion()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }


    /**
     * actualiza
     */
    @PutMapping("/{id}")
    public ResponseEntity<AdopcionDTO> update(
            @PathVariable Long id,
            @RequestBody AdopcionDTO dto
    ) {

        Optional<Adopcion> adopcionOpt = adopcionService.findById(id);

        if (adopcionOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Adopcion adopcion = adopcionOpt.get();
        adopcion.setNombreAdoptante(dto.nombreAdoptante());
        adopcion.setFechaAdopcion(dto.fechaAdopcion());
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
