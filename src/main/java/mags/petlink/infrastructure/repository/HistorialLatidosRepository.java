package mags.petlink.infrastructure.repository;

import mags.petlink.domain.model.HistorialLatidos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistorialLatidosRepository extends JpaRepository<HistorialLatidos, Long> {

    List<HistorialLatidos> findByMascotaId(Long mascotaId);
}
