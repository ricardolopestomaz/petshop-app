package dev.ricardolptz.Petshop.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TipoServicoDTO {
    private Long id;
    private String nome;
    private String descricao;
    private boolean ativo;
}
