package mags.petlink.application.service;

import mags.petlink.domain.model.HistorialLatidos;
import mags.petlink.infrastructure.repository.HistorialLatidosRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistorialLatidosService {

    private final HistorialLatidosRepository historialLatidosRepository;

    public HistorialLatidosService(HistorialLatidosRepository historialLatidosRepository) {
        this.historialLatidosRepository = historialLatidosRepository;
    }

    public List<HistorialLatidos> obtenerPorMascota(Long mascotaId) {
        return historialLatidosRepository.findByMascotaId(mascotaId);
    }
}
