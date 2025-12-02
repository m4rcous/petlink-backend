package mags.petlink.infrastructure.repository;

import mags.petlink.domain.model.HistorialLatidos;
import mags.petlink.domain.model.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HistorialLatidosRepository extends JpaRepository<HistorialLatidos, Long> {

    List<HistorialLatidos> findTop6ByMascotaOrderByTiempoDesc(Mascota mascota);

    long countByMascota(Mascota mascota);
    Optional<HistorialLatidos> findFirstByMascotaOrderByTiempoAsc(Mascota mascota);
    List<HistorialLatidos> findByMascotaId(Long mascotaId);
}
