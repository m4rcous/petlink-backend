package mags.petlink.application.service;

import mags.petlink.domain.enums.EstadoCollar;
import mags.petlink.domain.model.Collar;
import mags.petlink.domain.model.Mascota;
import mags.petlink.infrastructure.repository.CollarRepository;
import mags.petlink.infrastructure.repository.MascotaRepository;
import mags.petlink.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class MascotaService {

    private final MascotaRepository mascotaRepository;
    private final CollarRepository collarRepository;

    public MascotaService(MascotaRepository mascotaRepository,
                          CollarRepository collarRepository) {
        this.mascotaRepository = mascotaRepository;
        this.collarRepository = collarRepository;
    }

    public Mascota crearMascota(String nombre, LocalTime horaIngresa) {
        Mascota mascota = Mascota.builder()
                .nombre(nombre)
                .horaIngresa(horaIngresa)
                .internado(false)
                .build();
        return mascotaRepository.save(mascota);
    }

    public Mascota obtenerPorId(Long id) {
        return mascotaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Mascota no encontrada con id " + id));
    }

    public Mascota cambiarEstadoInternado(Long mascotaId, boolean internado) {
        Mascota mascota = obtenerPorId(mascotaId);
        mascota.setInternado(internado);
        return mascotaRepository.save(mascota);
    }

    public Mascota vincularCollar(Long mascotaId, Long collarId) {
        Mascota mascota = obtenerPorId(mascotaId);

        Collar collar = collarRepository.findById(collarId)
                .orElseThrow(() -> new NotFoundException("Collar no encontrado con id " + collarId));

        // Asignar bidireccionalmente
        mascota.setCollar(collar);
        collar.setMascota(mascota);
        collar.setEstado(EstadoCollar.OCUPADO);

        collarRepository.save(collar);
        return mascotaRepository.save(mascota);
    }

    public Mascota desvincularCollar(Long mascotaId) {
        Mascota mascota = obtenerPorId(mascotaId);

        Collar collar = mascota.getCollar();
        if (collar != null) {
            mascota.setCollar(null);
            collar.setMascota(null);
            collar.setEstado(EstadoCollar.DISPONIBLE);
            collarRepository.save(collar);
        }

        return mascotaRepository.save(mascota);
    }
}
