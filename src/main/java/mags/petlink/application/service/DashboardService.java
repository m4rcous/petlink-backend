package mags.petlink.application.service;

import mags.petlink.api.dto.response.DashboardResponse;
import mags.petlink.api.dto.response.DashboardResponse.MascotaMonitoreoDTO;
import mags.petlink.domain.model.HistorialLatidos;
import mags.petlink.domain.model.Mascota;
import mags.petlink.infrastructure.repository.HistorialLatidosRepository;
import mags.petlink.infrastructure.repository.MascotaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class DashboardService {

    private final MascotaRepository mascotaRepository;
    private final HistorialLatidosRepository historialLatidosRepository;

    public DashboardService(MascotaRepository mascotaRepository,
                            HistorialLatidosRepository historialLatidosRepository) {
        this.mascotaRepository = mascotaRepository;
        this.historialLatidosRepository = historialLatidosRepository;
    }

    public DashboardResponse obtenerDashboard() {
        // Obtener solo mascotas internadas
        List<Mascota> mascotasInternadas = mascotaRepository.findAll().stream()
                .filter(Mascota::isInternado)
                .toList();

        int criticos = 0;
        int estables = 0;
        int alertas = 0;

        List<MascotaMonitoreoDTO> mascotasDTO = new ArrayList<>();

        for (Mascota mascota : mascotasInternadas) {
            // Obtener último latido
            List<HistorialLatidos> historial = historialLatidosRepository
                    .findTop6ByMascotaOrderByTiempoDesc(mascota);

            Integer ultimoBpm = null;
            String ultimaActualizacion = null;

            if (!historial.isEmpty()) {
                HistorialLatidos ultimo = historial.get(0);
                ultimoBpm = ultimo.getBpm();
                ultimaActualizacion = ultimo.getTiempo().toString();
            }

            // Calcular estado LED basado en BPM
            String estadoLED = calcularEstadoLED(ultimoBpm);

            // Contar por estado
            switch (estadoLED) {
                case "Rojo" -> criticos++;
                case "Verde" -> estables++;
                case "Amarillo" -> alertas++;
            }

            mascotasDTO.add(new MascotaMonitoreoDTO(
                    mascota.getId(),
                    mascota.getNombre(),
                    ultimoBpm,
                    estadoLED,
                    ultimaActualizacion
            ));
        }

        return new DashboardResponse(
                mascotasInternadas.size(),
                criticos,
                estables,
                alertas,
                mascotasDTO
        );
    }

    private String calcularEstadoLED(Integer bpm) {
        if (bpm == null) {
            return "Amarillo"; // Sin datos = alerta
        }
        if (bpm < 40 || bpm > 160) {
            return "Rojo"; // Crítico
        }
        if (bpm < 60 || bpm > 140) {
            return "Amarillo"; // Alerta
        }
        return "Verde"; // Normal/Estable
    }
}