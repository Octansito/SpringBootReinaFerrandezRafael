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
/**
 * Controlador REST de voluntarios.
 * Expone endpoints para gestionar adopciones (CRUD) y consultas.
 */
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
    public ResponseEntity<Page<VoluntarioDTO>> findAll(
            @PageableDefault(size = 10) Pageable pageable
    ) {

        Page<VoluntarioDTO> page = voluntarioService.findAll(pageable)
                .map(v -> new VoluntarioDTO(
                        v.getDni(),
                        v.getNombre(),
                        v.getRol(),
                        v.getAntiguedad()
                ));

        return ResponseEntity.ok(page);
    }


    /**
     * GET por rol
     * /api/voluntarios/rol/{rol}
     */
    @GetMapping("/rol/{rol}")
    public ResponseEntity<List<VoluntarioDTO>> findByRol(
            @PathVariable VoluntarioRol rol
    ) {

        List<VoluntarioDTO> lista = voluntarioService.findByRol(rol)
                .stream()
                .map(v -> new VoluntarioDTO(
                        v.getDni(),
                        v.getNombre(),
                        v.getRol(),
                        v.getAntiguedad()
                ))
                .toList();

        return ResponseEntity.ok(lista);
    }


    /**
     * GET por animal (JPQL)
     * Busca la cantidad de voluntarios encargados de un animal
     * /api/voluntarios/animal/{nombre}
     */
    @GetMapping("/animal/{nombre}")
    public ResponseEntity<List<VoluntarioDTO>> findByAnimal(
            @PathVariable String nombre
    ) {

        List<VoluntarioDTO> lista = voluntarioService.buscarVoluntariosPorAnimal(nombre)
                .stream()
                .map(v -> new VoluntarioDTO(
                        v.getDni(),
                        v.getNombre(),
                        v.getRol(),
                        v.getAntiguedad()
                ))
                .toList();

        return ResponseEntity.ok(lista);
    }


    /**
     * Obtiene uno por dni
     */
    @GetMapping("/{dni}")
    public ResponseEntity<VoluntarioDTO> findById(@PathVariable String dni) {

        Optional<Voluntario> voluntarioOpt = voluntarioService.findById(dni);

        if (voluntarioOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Voluntario v = voluntarioOpt.get();

        VoluntarioDTO dto = new VoluntarioDTO(
                v.getDni(),
                v.getNombre(),
                v.getRol(),
                v.getAntiguedad()
        );

        return ResponseEntity.ok(dto);
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
    public ResponseEntity<VoluntarioDTO> create(
            @RequestBody VoluntarioDTO dto
    ) {

        Voluntario voluntario = new Voluntario();
        voluntario.setDni(dto.dni());
        voluntario.setNombre(dto.nombre());
        voluntario.setRol(dto.rol());
        voluntario.setAntiguedad(dto.antiguedad());

        Voluntario guardado = voluntarioService.save(voluntario);

        VoluntarioDTO creado = new VoluntarioDTO(
                guardado.getDni(),
                guardado.getNombre(),
                guardado.getRol(),
                guardado.getAntiguedad()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }


    /**
     * actualiza
     */
    @PutMapping("/{dni}")
    public ResponseEntity<VoluntarioDTO> update(
            @PathVariable String dni,
            @RequestBody VoluntarioDTO dto
    ) {

        Optional<Voluntario> voluntarioOpt = voluntarioService.findById(dni);

        if (voluntarioOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Voluntario voluntario = voluntarioOpt.get();
        voluntario.setNombre(dto.nombre());
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
     * elimina
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
