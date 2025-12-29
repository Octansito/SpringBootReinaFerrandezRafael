package es.severo.ud4.controller;

import es.severo.ud4.dto.VoluntarioDTO;
import es.severo.ud4.entities.Voluntario;
import es.severo.ud4.entities.VoluntarioRol;
import es.severo.ud4.service.VoluntarioService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/voluntarios")
public class VoluntarioController {

    private final VoluntarioService voluntarioService;

    public VoluntarioController(VoluntarioService voluntarioService) {
        this.voluntarioService = voluntarioService;
    }

    /**
     * GET ALL (paginado)
     * /api/voluntarios
     */
    @GetMapping
    public ResponseEntity<Page<Voluntario>> findAll(
            @PageableDefault(size = 10) Pageable pageable
    ) {

        Page<Voluntario> page = voluntarioService.findAll(pageable);
        return ResponseEntity.ok(page);
    }


    /**
     * GET por rol
     * /api/voluntarios/rol/{rol}
     */
    @GetMapping("/rol/{rol}")
    public ResponseEntity<List<Voluntario>> findByRol(
            @PathVariable VoluntarioRol rol
    ) {

        List<Voluntario> lista = voluntarioService.findByRol(rol);
        return ResponseEntity.ok(lista);
    }

    /**
     * GET por animal (JPQL)
     * Busca la cantidad de voluntarios encargados de un animal
     * /api/voluntarios/animal/{nombre}
     */
    @GetMapping("/animal/{nombre}")
    public ResponseEntity<List<Voluntario>> findByAnimal(
            @PathVariable String nombre
    ) {

        List<Voluntario> lista = voluntarioService.buscarVoluntariosPorAnimal(nombre);
        return ResponseEntity.ok(lista);
    }

    /**
     * Obtiene uno por dni
     */
    @GetMapping("/{dni}")
    public ResponseEntity<Voluntario> findById(@PathVariable String dni) {

        Optional<Voluntario> voluntarioOpt = voluntarioService.findById(dni);

        if (voluntarioOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(voluntarioOpt.get());
    }
    /**
     * GET animales asignados a un voluntario
     * /api/voluntarios/{dni}/animales
     */
    @GetMapping("/{dni}/animales")
    public ResponseEntity<List<String>> findAnimalesByVoluntario(
            @PathVariable String dni
    ) {
        Optional<Voluntario> voluntarioOpt = voluntarioService.findById(dni);

        if (voluntarioOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Voluntario voluntario = voluntarioOpt.get();

        List<String> animales = voluntario.getAnimales()
                .stream()
                .map(a -> a.getNombre())
                .toList();

        return ResponseEntity.ok(animales);
    }

    /**
     * crea
     */
    @PostMapping
    public ResponseEntity<Voluntario> create(
            @RequestBody Voluntario voluntario
    ) {

        Voluntario guardado = voluntarioService.save(voluntario);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    /**
     * actualiza
     */
    @PutMapping("/{dni}")
    public ResponseEntity<Voluntario> update(
            @PathVariable String dni,
            @RequestBody Voluntario voluntario
    ) {

        Optional<Voluntario> voluntarioOpt = voluntarioService.findById(dni);

        if (voluntarioOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        voluntario.setDni(dni);
        Voluntario actualizado = voluntarioService.save(voluntario);

        return ResponseEntity.ok(actualizado);
    }
    /**
     * Actualización parcial (rol y antigüedad)
     * /api/voluntarios/{dni}
     */
    @PatchMapping("/{dni}")
    public ResponseEntity<VoluntarioDTO> updatePartial(
            @PathVariable String dni,
            @RequestBody VoluntarioDTO dto
    ) {
        Optional<Voluntario> voluntarioOpt = voluntarioService.findById(dni);

        if (voluntarioOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Voluntario voluntario = voluntarioOpt.get();

        // SOLO campos modificables
        voluntario.setRol(dto.rol());
        voluntario.setAntiguedad(dto.antiguedad());

        Voluntario actualizado = voluntarioService.save(voluntario);

        VoluntarioDTO dtoActualizado = new VoluntarioDTO(
                actualizado.getDni(),
                actualizado.getNombre(),
                actualizado.getRol(),
                actualizado.getAntiguedad()
        );

        return ResponseEntity.ok(dtoActualizado);
    }

    /**
     * DELETE
     */
    @DeleteMapping("/{dni}")
    public ResponseEntity<Void> delete(@PathVariable String dni) {

        if (voluntarioService.findById(dni).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        voluntarioService.deleteById(dni);
        return ResponseEntity.noContent().build();
    }
}
