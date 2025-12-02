package mags.petlink.api.dto.request;

public record MascotaCreateRequest(
        String nombre,
        String horaIngresa
) {
}
