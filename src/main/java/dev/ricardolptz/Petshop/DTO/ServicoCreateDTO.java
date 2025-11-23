package dev.ricardolptz.Petshop.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServicoCreateDTO {
    private String nome;
    private String descricao;
    private Double preco;
    private Integer duracaoMinutos;
    private Long tipoServicoId;
}