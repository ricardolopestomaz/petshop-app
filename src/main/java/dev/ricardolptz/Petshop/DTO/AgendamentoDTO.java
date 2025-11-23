package dev.ricardolptz.Petshop.DTO;

import dev.ricardolptz.Petshop.model.Agendamento;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class AgendamentoDTO {
    private Long id;
    private LocalDateTime dataHora;
    private Agendamento.Status status;
    private String observacoes;

    private Long petId;
    private String nomePet;
    private String nomeDono;

    private Long servicoId;
    private String nomeServico;
    private Double preco;
}