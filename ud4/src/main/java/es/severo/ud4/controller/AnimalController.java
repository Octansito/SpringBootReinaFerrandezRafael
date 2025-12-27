package es.severo.ud4.controller;

import es.severo.ud4.dto.AnimalDTO;

import es.severo.ud4.entities.AnimalEstado;
import es.severo.ud4.entities.AnimalTipo;
import es.severo.ud4.service.AnimalService;
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
@RequestMapping("/api/animales")
public class AnimalController {

    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    /**
     * Obtener todos los animales
     */
    @GetMapping
    @Operation(
            description = "Obtener todos los animales",
            summary = "Devuelve la lista completa de animales registrados"
    )
    @ApiResponses(
            value={@ApiResponse(responseCode = "200", description = "Animal encontrado correctamente"),
                    @ApiResponse(responseCode = "404", description = "Animal no encontrado")


    })
    public ResponseEntity<List<AnimalDTO>> getAll() {
        return ResponseEntity.ok(animalService.findAll());
    }

    /**
     * Buscar animales por id
     */
    @GetMapping("/{id}")
    public ResponseEntity<AnimalDTO> getById(@PathVariable(name="id")Long id){
        Optional<AnimalDTO> animal=animalService.findById(id);
        if(animal.isPresent()){
            return ResponseEntity.ok(animal.get());
        }
        return  ResponseEntity.noContent().build();
    }
    /**
     * Obtener animales paginados
     */
    @GetMapping("/page")
    public ResponseEntity<List<AnimalDTO>> getPageableAnimals(@RequestParam(name="page") int nPage, @RequestParam (name="size") int nItems){
        Page<AnimalDTO> animalDTOPage=animalService.findAllPageable (nPage-1, nItems);
        return ResponseEntity.ok(animalDTOPage.getContent());
    }

    /**
     * Ordenar animales
     */
    @GetMapping("/sort")
    public ResponseEntity<List<AnimalDTO>> getSortUsers(@RequestParam(name = "sort_by") String field, @RequestParam(name = "order_by") String orderBy){
        return ResponseEntity.ok(animalService.findAllSort(field, orderBy));
    }

    /**
     * Filtrar por estado
     */
    @GetMapping("/estado")
    public ResponseEntity<Page<AnimalDTO>> getByEstado(
            @RequestParam AnimalEstado estado,
            @RequestParam int page,
            @RequestParam int size) {

        return ResponseEntity.ok(
                animalService.findByEstado(estado, page, size)
        );
    }
    /**
     * Filtrar por tipo
     */
    @GetMapping("/tipo")
    public ResponseEntity<Page<AnimalDTO>> getByTipo(
            @RequestParam AnimalTipo tipo,
            @RequestParam int page,
            @RequestParam int size) {

        return ResponseEntity.ok(
                animalService.findByTipo(tipo, page, size)
        );
    }

    /**
     * Crear animal
     */
    @PostMapping
    public ResponseEntity<AnimalDTO> create(@RequestBody AnimalDTO dto){
        AnimalDTO creado= animalService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    /**
     * Actualizar animal completo
     */
    @PutMapping("/{id}")
    public ResponseEntity<AnimalDTO> updateAnimal(@RequestBody AnimalDTO dto, @PathVariable Long id){
        Optional<AnimalDTO> optionalAnimal=animalService.findById(id);
        if(optionalAnimal.isPresent()){
            AnimalDTO animalDB=optionalAnimal.get();
            animalDB.setNombre(dto.nombre());
            animalDB.setTipo(dto.tipo());
            animalDB.setEdad(dto.edad());
            animalDB.setEstado(dto.estado());
            animalService.save(animalDB);
            return ResponseEntity.ok(animalDB);
        }
        return  ResponseEntity.notFound().build();
    }
    /**
     * Actualizar datos parciales del animal (edad, estado)
     */
    @PatchMapping("/{id}")
    public ResponseEntity<AnimalDTO> updatePartiallu(@PathVariable Long id, @RequestBody AnimalDTO dto){
        Optional<AnimalDTO> optionalAnimal=animalService.findById(id);
        if(optionalAnimal.isPresent()){
            AnimalDTO animalDB= optionalAnimal.get();
            animalDB.setEdad(dto.edad());
            animalDB.setEstado(dto.estado());
            animalService.save(animalDB);
            return ResponseEntity.ok(animalDB);
        }
        return  ResponseEntity.notFound().build();
    }
    /**
     * Borrar animal
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        Optional<AnimalDTO> animalDTO=animalService.findById(id);
        if(animalDTO.isPresent()){
            animalService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return  ResponseEntity.notFound().build();
    }


}
