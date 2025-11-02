package dev.ricardolptz.Petshop.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TipoPetResponseDTO {
    private Long id;
    private String nome;

    public TipoPetResponseDTO() {
    }

    public TipoPetResponseDTO(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

}
