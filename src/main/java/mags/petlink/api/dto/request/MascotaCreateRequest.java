package mags.petlink.api.dto.request;

import mags.petlink.domain.enums.EstadoSalud;

public record MascotaCreateRequest(
        String nombre,
        String especie,
        Integer edad,
        EstadoSalud estadoSalud,
        String raza,
        String horaIngresa
) {
}
