package mags.petlink.api.dto.response;

import java.util.List;

public record MascotaMonitorResponse(
        String nombre,
        Integer edad,
        String raza,
        String horaIngresa,
        Integer currentHeartRate,
        String estadoSalud,
        List<Integer> lastSixHeartRate
) {}
