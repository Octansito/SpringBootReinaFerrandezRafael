package es.severo.ud4.controller;

import es.severo.ud4.dto.AdopcionDTO;
import es.severo.ud4.dto.AnimalDTO;
import es.severo.ud4.dto.GrupoDTO;
import es.severo.ud4.service.GrupoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/grupos")
public class GrupoController {
    private final GrupoService grupoService;

    public GrupoController(GrupoService grupoService) {
        this.grupoService = grupoService;
    }
    /**
     * Obtener todas los grupos
     */

    @GetMapping
    @Operation(
            description = "Obtener todos los grupos",
            summary = "Devuelve la lista completa de los grupos"
    )
    @ApiResponses(
            value={@ApiResponse(responseCode = "200", description = "Grupo encontrado correctamente"),
                    @ApiResponse(responseCode = "404", description = "Grupo no encontrado")


            })
    public ResponseEntity<List<GrupoDTO>> getAll() {
        return ResponseEntity.ok(grupoService.findAll());
    }
    /**
     * Obtener grupo por id
     */
    @GetMapping("/{id}")
    public ResponseEntity<GrupoDTO> getById(@PathVariable(name="id")Long id){
        Optional<GrupoDTO> grupo=grupoService.findById(id);
        if(grupo.isPresent()){
            return ResponseEntity.ok(grupo.get());
        }
        return  ResponseEntity.noContent().build();
    }
    /**
     * Crear grupo
     */
    @PostMapping
    public ResponseEntity<GrupoDTO> create(@RequestBody GrupoDTO dto){
        GrupoDTO creado= grupoService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }
    /**
     * Actualizar grupo completa
     */
    @PutMapping("/{id}")
    public ResponseEntity<GrupoDTO> updateGrupo(@RequestBody GrupoDTO dto, @PathVariable Long id){
        Optional<GrupoDTO> optionalGrupo=grupoService.findById(id);
        if(optionalGrupo.isPresent()){
            GrupoDTO grupoDb=optionalGrupo.get();
            grupoDb.setDiaSemana(dto.diaSemana());
            grupoDb.setTurno(dto.turno());
            grupoDb.setResponsableDni(dto.responsableDni());
            grupoService.save(grupoDb);
            return ResponseEntity.ok(grupoDb);
        }
        return  ResponseEntity.notFound().build();
    }
    /**
     * Actualizar datos parciales de una grupo (responsable)
     */
    @PatchMapping("/{id}")
    public ResponseEntity<GrupoDTO> updatePartially(@PathVariable Long id, @RequestBody GrupoDTO dto){
        Optional<GrupoDTO> optionalGrupo=grupoService.findById(id);
        if(optionalGrupo.isPresent()){
            GrupoDTO grupoDb= optionalGrupo.get();
            grupoDb.setResponsableDni(dto.responsableDni());
            grupoService.save(grupoDb);
            return ResponseEntity.ok(grupoDb);
        }
        return  ResponseEntity.notFound().build();
    }
    /**
     * Borrar grupo
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        Optional<GrupoDTO> grupoDTO=grupoService.findById(id);
        if(grupoDTO.isPresent()){
            grupoService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return  ResponseEntity.notFound().build();
    }
    /**
     * Obtener grupos paginados
     */
    @GetMapping("/page")
    public ResponseEntity<List<GrupoDTO>> getPageableGrupos(@RequestParam(name="page") int nPage, @RequestParam (name="size") int nItems){
        Page<GrupoDTO> grupoDTOPage=grupoService.findAllPageable (nPage-1, nItems);
        return ResponseEntity.ok(grupoDTOPage.getContent());
    }
    /**
     * Subruta: Voluntarios de un grupo
     */
    /**
     * Subruta de animales en una adopcion
     */
    @GetMapping("/{id}/voluntarios")
    public ResponseEntity<List<GrupoDTO>> getVoluntariosByGrupo(@PathVariable Long id) {
        return ResponseEntity.ok(
                grupoService.findVoluntariosByGrupo(id)
        );
    }
}
