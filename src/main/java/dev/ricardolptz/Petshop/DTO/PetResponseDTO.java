package dev.ricardolptz.Petshop.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PetResponseDTO {
    private Long id;
    private String nome;
    private Integer idade;
    private String raca;
    private double peso;

    private TipoPetResponseDTO tipoPet;

    private UsuarioResumoDTO usuario;

}
