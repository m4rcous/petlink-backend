package mags.petlink.infrastructure.repository;

import mags.petlink.domain.model.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MascotaRepository extends JpaRepository<Mascota, Long> {
}
