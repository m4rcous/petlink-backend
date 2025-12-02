package mags.petlink.api.dto.response;

public record MascotaResponse(
        Long id,
        String nombre,
        String especie,
        Integer edad,
        String estadoSalud,
        String raza,
        boolean internado,
        String horaIngresa,
        Long collarId
) {
}
