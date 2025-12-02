package mags.petlink.api.controller;

import mags.petlink.api.dto.request.SensorDataRequest;
import mags.petlink.application.service.SensorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sensor")
public class SensorController {
    private final SensorService sensorService;

    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @PostMapping("/heartbeat")
    public ResponseEntity<Void> recibirLatido(@RequestBody SensorDataRequest request) {
        sensorService.procesarLectura(request);
        return ResponseEntity.accepted().build(); // 202 Accepted
    }
}
