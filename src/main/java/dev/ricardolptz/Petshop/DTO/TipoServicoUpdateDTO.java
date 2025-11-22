package dev.ricardolptz.Petshop.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TipoServicoUpdateDTO {
    private String nome;
    private String descricao;
    private Boolean ativo; // Use Boolean (wrapper) para saber se veio nulo
}