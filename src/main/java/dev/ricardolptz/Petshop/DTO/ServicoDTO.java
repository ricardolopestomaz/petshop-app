package dev.ricardolptz.Petshop.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServicoDTO {
    private Long id;
    private String nome;
    private String descricao;
    private double preco;
    private Integer duracaoMinutos;
    private boolean ativo;

    private Long tipoServicoId;
    private String nomeTipoServico;
}