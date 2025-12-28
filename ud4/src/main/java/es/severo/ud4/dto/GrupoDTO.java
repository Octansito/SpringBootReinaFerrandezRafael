package es.severo.ud4.dto;

import es.severo.ud4.entities.DiaSemana;
import es.severo.ud4.entities.Turno;

public record GrupoDTO(Long id, DiaSemana diaSemana, Turno turno, String responsableDni) {
}
