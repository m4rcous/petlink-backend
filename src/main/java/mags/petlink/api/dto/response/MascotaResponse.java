package mags.petlink.api.dto.response;

public record MascotaResponse(
        Long id,
        String nombre,
        String horaIngresa,
        boolean internado,
        Long collarId
) {
}
