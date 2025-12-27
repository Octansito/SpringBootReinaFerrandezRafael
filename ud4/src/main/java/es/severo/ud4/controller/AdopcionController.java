package es.severo.ud4.controller;

import es.severo.ud4.dto.AdopcionDTO;
import es.severo.ud4.dto.AnimalDTO;
import es.severo.ud4.service.AdopcionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
     * Obtener todas las adopciones
     */

    @GetMapping
    @Operation(
            description = "Obtener todos las adopciones",
            summary = "Devuelve la lista completa de las adopciones registradas"
    )
    @ApiResponses(
            value={@ApiResponse(responseCode = "200", description = "Adopcion encontrado correctamente"),
                    @ApiResponse(responseCode = "404", description = "Adopcion no encontrado")


            })
    public ResponseEntity<List<AdopcionDTO>> getAll() {
        return ResponseEntity.ok(adopcionService.findAll());
    }
    /**
     * Buscar adopcion por id
     */
    @GetMapping("/{id}")
    public ResponseEntity<AdopcionDTO> getById(@PathVariable(name="id")Long id){
        Optional<AdopcionDTO> adopcion=adopcionService.findById(id);
        if(adopcion.isPresent()){
            return ResponseEntity.ok(adopcion.get());
        }
        return  ResponseEntity.noContent().build();
    }
    /**
     * Crear adopcion
     */
    @PostMapping
    public ResponseEntity<AdopcionDTO> create(@RequestBody AdopcionDTO dto){
        AdopcionDTO creado= adopcionService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }
    /**
     * Actualizar adopcion completa
     */
    @PutMapping("/{id}")
    public ResponseEntity<AdopcionDTO> updateAdopcion(@RequestBody AdopcionDTO dto, @PathVariable Long id){
        Optional<AdopcionDTO> optionalAdopcion=adopcionService.findById(id);
        if(optionalAdopcion.isPresent()){
            AdopcionDTO adopcionDTO=optionalAdopcion.get();
            adopcionDTO.setNombreAdoptante(dto.nombreAdoptante());
            adopcionDTO.setFechaAdopcion(dto.fechaAdopcion());
            adopcionDTO.setDireccion(dto.direccion());
            adopcionService.save(adopcionDTO);
            return ResponseEntity.ok(adopcionDTO);
        }
        return  ResponseEntity.notFound().build();
    }
    /**
     * Actualizar datos parciales de una adopcion (direccion)
     */
    @PatchMapping("/{id}")
    public ResponseEntity<AdopcionDTO> updatePartially(@PathVariable Long id, @RequestBody AdopcionDTO dto){
        Optional<AdopcionDTO> optionalAdopcion=adopcionService.findById(id);
        if(optionalAdopcion.isPresent()){
            AdopcionDTO adopcionDB= optionalAdopcion.get();
            adopcionDB.setDireccion(dto.direccion());
            adopcionService.save(adopcionDB);
            return ResponseEntity.ok(adopcionDB);
        }
        return  ResponseEntity.notFound().build();
    }
    /**
     * Borrar adopcion
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        Optional<AdopcionDTO> adopcionDTO=adopcionService.findById(id);
        if(adopcionDTO.isPresent()){
            adopcionService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return  ResponseEntity.notFound().build();
    }
    /**
     * Subruta de animales en una adopcion
     */
    @GetMapping("/{id}/animales")
    public ResponseEntity<List<AnimalDTO>> getAnimalesByAdopcion(@PathVariable Long id) {
        return ResponseEntity.ok(
                adopcionService.findAnimalesByAdopcion(id)
        );
    }

}
