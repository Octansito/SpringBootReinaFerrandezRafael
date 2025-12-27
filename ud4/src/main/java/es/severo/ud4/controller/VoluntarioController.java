package es.severo.ud4.controller;

import es.severo.ud4.dto.AnimalDTO;
import es.severo.ud4.dto.GrupoDTO;
import es.severo.ud4.dto.VoluntarioDTO;
import es.severo.ud4.service.VoluntarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping ("/api/voluntarios")
public class VoluntarioController {
    private final VoluntarioService voluntarioService;

    public VoluntarioController(VoluntarioService voluntarioService) {
        this.voluntarioService = voluntarioService;
    }
    /**
     * Obtener todas los voluntarios
     */

    @GetMapping
    @Operation(
            description = "Obtener todos los voluntarios",
            summary = "Devuelve la lista completa de los voluntarios"
    )
    @ApiResponses(
            value={@ApiResponse(responseCode = "200", description = "Voluntario encontrado correctamente"),
                    @ApiResponse(responseCode = "404", description = "Voluntario no encontrado")


            })
    public ResponseEntity<List<VoluntarioDTO>> getAll() {
        return ResponseEntity.ok(voluntarioService.findAll());
    }
    /**
     * Obtener voluntario por dni
     */
    @GetMapping("/{dni}")
    public ResponseEntity<VoluntarioDTO> getById(@PathVariable(name="dni")String dni){
        Optional<VoluntarioDTO> voluntario=voluntarioService.findById(dni);
        if(voluntario.isPresent()){
            return ResponseEntity.ok(voluntario.get());
        }
        return  ResponseEntity.noContent().build();
    }
    /**
     * Crear voluntario
     */
    @PostMapping
    public ResponseEntity<VoluntarioDTO> create(@RequestBody VoluntarioDTO dto){
        VoluntarioDTO creado= voluntarioService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }
    /**
     * Actualizar voluntario completo
     */
    @PutMapping("/{id}")
    public ResponseEntity<VoluntarioDTO> updateVoluntario(@RequestBody VoluntarioDTO dto, @PathVariable String dni){
        Optional<GrupoDTO> optionalVoluntario=voluntarioService.findById(dni);
        if(optionalVoluntario.isPresent()){
            VoluntarioDTO voluntarioDTO=voluntarioService.get();
            voluntarioDTO.setDni(dto.dni());
            voluntarioDTO.setNombre(dto.nombre());
            voluntarioDTO.setRol(dto.rol());
            voluntarioDTO.setAntiguedad(dto.antiguedad());
            voluntarioService.save(voluntarioDTO);
            return ResponseEntity.ok(voluntarioDTO);
        }
        return  ResponseEntity.notFound().build();
    }
    /**
     * Actualizar datos parciales de un voluntario(responsable)
     */
    @PatchMapping("/{id}")
    public ResponseEntity<VoluntarioDTO> updatePartially(@PathVariable String dni, @RequestBody VoluntarioDTO dto){
        Optional<VoluntarioDTO> optionalVoluntario=voluntarioService.findById(dni);
        if(optionalVoluntario.isPresent()){
            VoluntarioDTO voluntarioDTO= optionalVoluntario.get();
            voluntarioDTO.setRol(dto.rol());
            voluntarioDTO.setAntiguedad(dto.antiguedad());
            voluntarioService.save(voluntarioDTO);
            return ResponseEntity.ok(voluntarioDTO);
        }
        return  ResponseEntity.notFound().build();
    }
    /**
     * Borrar voluntario
     */
    @DeleteMapping("{dni}")
    public ResponseEntity<Void> delete(@PathVariable String dni){
        Optional<VoluntarioDTO> voluntarioDTO=voluntarioService.findById(dni);
        if(voluntarioDTO.isPresent()){
            voluntarioDTO.deleteById(dni);
            return ResponseEntity.noContent().build();
        }
        return  ResponseEntity.notFound().build();
    }
    /**
     * Subruta: animales atendidos por un voluntario
     */
    @GetMapping("/{dni}/animales")

    public ResponseEntity<List<AnimalDTO>> getAnimalesByVoluntario(@PathVariable String dni) {
        return ResponseEntity.ok(
                voluntarioService.findAnimalesByVoluntario(dni)
        );
    }

    /**
     * Subruta: grupos del voluntario
     */
    @GetMapping("/{dni}/grupos")
    public ResponseEntity<?> getGruposByVoluntario(@PathVariable String dni) {
        return ResponseEntity.ok(
                voluntarioService.findGruposByVoluntario(dni)
        );
    }
}
