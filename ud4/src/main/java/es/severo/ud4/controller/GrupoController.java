package es.severo.ud4.controller;

import es.severo.ud4.dto.GrupoDTO;
import es.severo.ud4.entities.DiaSemana;
import es.severo.ud4.entities.Grupo;
import es.severo.ud4.entities.Voluntario;
import es.severo.ud4.service.GrupoService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/grupos")
public class GrupoController {

    private final GrupoService grupoService;

    public GrupoController(GrupoService grupoService) {
        this.grupoService = grupoService;
    }

    /**
     * GET ALL (paginado)
     */
    @GetMapping
    public ResponseEntity<Page<GrupoDTO>> findAll(
            @PageableDefault(size = 10) Pageable pageable
    ) {

        Page<GrupoDTO> page = grupoService.findAll(pageable)
                .map(g -> new GrupoDTO(
                        g.getId(),
                        g.getDiaSemana(),
                        g.getTurno(),
                        g.getResponsable().getDni()
                ));

        return ResponseEntity.ok(page);
    }

    /**
     * GET por día de la semana
     * /api/grupos/dia/Lunes
     */
    @GetMapping("/dia/{diaSemana}")
    public ResponseEntity<List<GrupoDTO>> findByDiaSemana(
            @PathVariable DiaSemana diaSemana
    ) {

        List<GrupoDTO> lista = grupoService.findByDiaSemana(diaSemana)
                .stream()
                .map(g -> new GrupoDTO(
                        g.getId(),
                        g.getDiaSemana(),
                        g.getTurno(),
                        g.getResponsable().getDni()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }

    /**
     * GET por responsable (JPQL)
     * Devuelve los grupos con el dni del responsable
     * /api/grupos/responsable/{dni}
     */
    @GetMapping("/responsable/{dni}")
    public ResponseEntity<List<GrupoDTO>> findGroupsByResponsable(
            @PathVariable String dni
    ) {

        List<GrupoDTO> lista = grupoService.buscarGruposPorResponsable(dni)
                .stream()
                .map(g -> new GrupoDTO(
                        g.getId(),
                        g.getDiaSemana(),
                        g.getTurno(),
                        g.getResponsable().getDni()
                ))
                .toList();

        return ResponseEntity.ok(lista);
    }
    /**
     * GET responsable de un grupo
     * Devuelve el responsable del grupo con id X
     * /api/grupos/{id}/responsable
     */
    @GetMapping("/{id}/responsable")
    public ResponseEntity<String> findResponsableByGrupo(
            @PathVariable Long id
    ) {
        Optional<Grupo> grupoOpt = grupoService.findById(id);

        if (grupoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Grupo grupo = grupoOpt.get();

        if (grupo.getResponsable() == null) {
            return ResponseEntity.notFound().build();
        }

        // Devuelve el DNI del responsable
        return ResponseEntity.ok(grupo.getResponsable().getDni());
    }

    /**
     * Obtiene 1 por id
     */
    @GetMapping("/{id}")
    public ResponseEntity<GrupoDTO> findById(@PathVariable Long id) {

        Optional<Grupo> grupoOpt = grupoService.findById(id);

        if (grupoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Grupo g = grupoOpt.get();

        GrupoDTO dto = new GrupoDTO(
                g.getId(),
                g.getDiaSemana(),
                g.getTurno(),
                g.getResponsable().getDni()
        );

        return ResponseEntity.ok(dto);
    }


    /**
     * crea
     */
    @PostMapping
    public ResponseEntity<GrupoDTO> create(@RequestBody GrupoDTO dto) {

        Grupo grupo = new Grupo();
        grupo.setDiaSemana(dto.diaSemana());
        grupo.setTurno(dto.turno());

        Voluntario responsable = new Voluntario();
        responsable.setDni(dto.responsableDni());
        grupo.setResponsable(responsable);

        Grupo guardado = grupoService.save(grupo);

        GrupoDTO resultado = new GrupoDTO(
                guardado.getId(),
                guardado.getDiaSemana(),
                guardado.getTurno(),
                guardado.getResponsable().getDni()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
    }

    /**
     * actualiza
     */
    @PutMapping("/{id}")
    public ResponseEntity<GrupoDTO> update(
            @PathVariable Long id,
            @RequestBody GrupoDTO dto
    ) {

        Optional<Grupo> grupoOpt = grupoService.findById(id);

        if (grupoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Grupo grupo = grupoOpt.get();
        grupo.setDiaSemana(dto.diaSemana());
        grupo.setTurno(dto.turno());

        Voluntario responsable = new Voluntario();
        responsable.setDni(dto.responsableDni());
        grupo.setResponsable(responsable);

        Grupo actualizado = grupoService.save(grupo);

        GrupoDTO resultado = new GrupoDTO(
                actualizado.getId(),
                actualizado.getDiaSemana(),
                actualizado.getTurno(),
                actualizado.getResponsable().getDni()
        );

        return ResponseEntity.ok(resultado);
    }
    /**
     * Actualización parcial (responsable)
     * /api/grupos/{id}
     */
    @PatchMapping("/{id}")
    public ResponseEntity<GrupoDTO> updatePartial(
            @PathVariable Long id,
            @RequestBody GrupoDTO dto
    ) {
        Optional<Grupo> grupoOpt = grupoService.findById(id);

        if (grupoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Grupo grupo = grupoOpt.get();

        // SOLO campo modificable: responsable
        Voluntario responsable = new Voluntario();
        responsable.setDni(dto.responsableDni());
        grupo.setResponsable(responsable);

        Grupo actualizado = grupoService.save(grupo);

        GrupoDTO dtoActualizado = new GrupoDTO(
                actualizado.getId(),
                actualizado.getDiaSemana(),
                actualizado.getTurno(),
                actualizado.getResponsable().getDni()
        );

        return ResponseEntity.ok(dtoActualizado);
    }

    /**
     * DELETE
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        if (grupoService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        grupoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
