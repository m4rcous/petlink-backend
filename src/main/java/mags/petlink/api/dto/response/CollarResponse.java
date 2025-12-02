package mags.petlink.api.dto.response;

public record CollarResponse(
        Long id,
        String code,
        String estado,
        Long mascotaId
) {}
