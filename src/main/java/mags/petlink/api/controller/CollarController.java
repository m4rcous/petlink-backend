package mags.petlink.api.controller;

import mags.petlink.api.dto.response.CollarResponse;
import mags.petlink.domain.model.Collar;
import mags.petlink.infrastructure.repository.CollarRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/collares")
public class CollarController {

    private final CollarRepository collarRepository;

    public CollarController(CollarRepository collarRepository) {
        this.collarRepository = collarRepository;
    }

    @GetMapping
    public ResponseEntity<List<CollarResponse>> listar() {
        List<CollarResponse> response = collarRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    private CollarResponse toResponse(Collar c) {
        Long mascotaId = c.getMascota() != null ? c.getMascota().getId() : null;
        return new CollarResponse(
                c.getId(),
                c.getCode(),
                c.getEstado().name(),
                mascotaId
        );
    }
}
