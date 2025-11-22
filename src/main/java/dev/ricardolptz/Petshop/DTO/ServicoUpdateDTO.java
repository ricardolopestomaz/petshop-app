package dev.ricardolptz.Petshop.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServicoUpdateDTO {
    private String nome;
    private String descricao;
    private Double preco;
    private Integer duracaoMinutos;
    private Boolean ativo;
    private Long tipoServicoId;
}