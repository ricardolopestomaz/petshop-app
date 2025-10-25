package com.petshop.api.repository.agendamento;

import com.petshop.api.model.agendamento.AgendamentoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendamentoRepository extends JpaRepository<AgendamentoModel, Long> {
}
