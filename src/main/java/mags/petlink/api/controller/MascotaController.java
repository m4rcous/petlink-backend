package mags.petlink.api.controller;

import mags.petlink.api.dto.request.CambiarInternadoRequest;
import mags.petlink.api.dto.request.MascotaCreateRequest;
import mags.petlink.api.dto.response.HistorialLatidosResponse;
import mags.petlink.api.dto.response.MascotaResponse;
import mags.petlink.application.service.HistorialLatidosService;
import mags.petlink.application.service.MascotaService;
import mags.petlink.domain.model.HistorialLatidos;
import mags.petlink.domain.model.Mascota;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/mascotas")
public class MascotaController {

    private final MascotaService mascotaService;
    private final HistorialLatidosService historialLatidosService;

    public MascotaController(MascotaService mascotaService,
                             HistorialLatidosService historialLatidosService) {
        this.mascotaService = mascotaService;
        this.historialLatidosService = historialLatidosService;
    }

    @PostMapping
    public ResponseEntity<MascotaResponse> crearMascota(@RequestBody MascotaCreateRequest request) {
        LocalTime hora = LocalTime.parse(request.horaIngresa()); // "HH:mm:ss"
        Mascota mascota = mascotaService.crearMascota(request.nombre(), hora);
        return ResponseEntity.ok(toMascotaResponse(mascota));
    }

    @PutMapping("/{id}/internado")
    public ResponseEntity<MascotaResponse> cambiarInternado(
            @PathVariable Long id,
            @RequestBody CambiarInternadoRequest request) {

        Mascota mascota = mascotaService.cambiarEstadoInternado(id, request.internado());
        return ResponseEntity.ok(toMascotaResponse(mascota));
    }

    @PutMapping("/{idMascota}/collar/{idCollar}")
    public ResponseEntity<MascotaResponse> vincularCollar(
            @PathVariable Long idMascota,
            @PathVariable Long idCollar) {

        Mascota mascota = mascotaService.vincularCollar(idMascota, idCollar);
        return ResponseEntity.ok(toMascotaResponse(mascota));
    }

    @PutMapping("/{idMascota}/collar/desvincular")
    public ResponseEntity<MascotaResponse> desvincularCollar(@PathVariable Long idMascota) {
        Mascota mascota = mascotaService.desvincularCollar(idMascota);
        return ResponseEntity.ok(toMascotaResponse(mascota));
    }

    @GetMapping("/{id}/historial")
    public ResponseEntity<List<HistorialLatidosResponse>> obtenerHistorial(@PathVariable Long id) {
        List<HistorialLatidos> registros = historialLatidosService.obtenerPorMascota(id);
        List<HistorialLatidosResponse> response = registros.stream()
                .map(this::toHistorialResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    private MascotaResponse toMascotaResponse(Mascota mascota) {
        Long collarId = mascota.getCollar() != null ? mascota.getCollar().getId() : null;
        return new MascotaResponse(
                mascota.getId(),
                mascota.getNombre(),
                mascota.getHoraIngresa().toString(),
                mascota.isInternado(),
                collarId
        );
    }

    private HistorialLatidosResponse toHistorialResponse(HistorialLatidos h) {
        return new HistorialLatidosResponse(
                h.getTiempo().toString(), // Instant -> ISO
                h.getBpm()
        );
    }
}