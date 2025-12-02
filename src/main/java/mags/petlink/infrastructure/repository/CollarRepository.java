package mags.petlink.infrastructure.repository;

import mags.petlink.domain.model.Collar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CollarRepository extends JpaRepository<Collar, Long> {
    Optional<Collar> findByCode(String code);
}
