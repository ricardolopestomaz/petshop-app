package dev.ricardolptz.Petshop.repository;

import dev.ricardolptz.Petshop.model.TipoPet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoPetRepository extends JpaRepository<TipoPet, Long> {
}
