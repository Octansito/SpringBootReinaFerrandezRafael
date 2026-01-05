package es.severo.ud4.dto;

import java.time.LocalDate;

public record AdopcionDTO(Long id, String nombreAdoptante, LocalDate fechaAdopcion, String direccion) {

}
