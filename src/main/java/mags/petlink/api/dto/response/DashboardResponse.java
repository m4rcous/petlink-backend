package mags.petlink.api.dto.response;

import java.util.List;

public record DashboardResponse(
    int total,
    int criticos,
    int estables,
    int alertas,
    List<MascotaMonitoreoDTO> mascotas
) {
    public record MascotaMonitoreoDTO(
        Long id,
        String nombre,
        Integer ritmoCardiaco,
        String estadoLED,
        String ultimaActualizacion
    ) {}
}