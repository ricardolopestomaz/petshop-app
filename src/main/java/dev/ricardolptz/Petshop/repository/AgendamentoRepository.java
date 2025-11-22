package dev.ricardolptz.Petshop.repository;

import dev.ricardolptz.Petshop.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    // Verifica se existe algum agendamento neste horário que NÃO esteja cancelado
    boolean existsByDataHoraAndStatusNot(LocalDateTime dataHora, Agendamento.Status status);
}