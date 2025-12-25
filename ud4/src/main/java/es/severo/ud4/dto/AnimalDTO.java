package es.severo.ud4.dto;

import es.severo.ud4.entities.AnimalEstado;
import es.severo.ud4.entities.AnimalTipo;

public record AnimalDTO(Long id, String nombre, AnimalTipo tipo, AnimalEstado estado, int edad) {
}
