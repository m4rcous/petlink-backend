package mags.petlink.application.service;

import mags.petlink.api.dto.request.SensorDataRequest;
import mags.petlink.domain.enums.EstadoCollar;
import mags.petlink.domain.model.Collar;
import mags.petlink.domain.model.HistorialLatidos;
import mags.petlink.domain.model.Mascota;
import mags.petlink.infrastructure.repository.CollarRepository;
import mags.petlink.infrastructure.repository.HistorialLatidosRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class SensorService {

    private final CollarRepository collarRepository;
    private final HistorialLatidosRepository historialLatidosRepository;

    public SensorService(CollarRepository collarRepository,
                         HistorialLatidosRepository historialLatidosRepository) {
        this.collarRepository = collarRepository;
        this.historialLatidosRepository = historialLatidosRepository;
    }

    public void procesarLectura(SensorDataRequest request) {
        String code = request.device_id();

        // 1. Buscar o crear collar
        Collar collar = collarRepository.findByCode(code)
                .orElseGet(() -> crearNuevoCollar(code));

        // 2. Solo guardar historial si el collar está vinculado a una mascota internada
        Mascota mascota = collar.getMascota();
        if (mascota != null && mascota.isInternado()) {

            // 💡 Limitar a 6 registros
            long count = historialLatidosRepository.countByMascota(mascota);
            if (count >= 6) {
                historialLatidosRepository.findFirstByMascotaOrderByTiempoAsc(mascota)
                        .ifPresent(historialLatidosRepository::delete);
            }

            HistorialLatidos registro = HistorialLatidos.builder()
                    .mascota(mascota)
                    .tiempo(Instant.parse(request.timestamp()))
                    .bpm(request.bpm())
                    .build();

            historialLatidosRepository.save(registro);
        }
    }

    private Collar crearNuevoCollar(String code) {
        Collar collar = Collar.builder()
                .code(code)
                .estado(EstadoCollar.DISPONIBLE)
                .build();
        return collarRepository.save(collar);
    }
}
