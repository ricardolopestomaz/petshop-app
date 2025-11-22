package dev.ricardolptz.Petshop.DTO;

import dev.ricardolptz.Petshop.model.Agendamento;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class AgendamentoUpdateDTO {
    private LocalDateTime dataHora;
    private Agendamento.Status status;
    private String observacoes;
}