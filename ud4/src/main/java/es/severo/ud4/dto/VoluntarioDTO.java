package es.severo.ud4.dto;

import es.severo.ud4.entities.VoluntarioRol;

public record VoluntarioDTO(String dni, String nombre, VoluntarioRol rol, int antiguedad) {
}
