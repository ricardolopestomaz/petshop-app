package com.petshop.api.repository.servico;

import com.petshop.api.model.servico.ServicoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicoRepository extends JpaRepository<ServicoModel, Long> {
}
