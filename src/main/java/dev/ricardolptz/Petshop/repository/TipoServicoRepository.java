package dev.ricardolptz.Petshop.repository;

import dev.ricardolptz.Petshop.model.TipoServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoServicoRepository extends JpaRepository<TipoServico, Long> {
}
