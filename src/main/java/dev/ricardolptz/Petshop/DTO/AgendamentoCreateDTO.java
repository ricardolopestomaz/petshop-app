package dev.ricardolptz.Petshop.DTO;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class AgendamentoCreateDTO {
    private LocalDateTime dataHora;
    private String observacoes;
    private Long petId;     // Só o ID
    private Long servicoId; // Só o ID
}