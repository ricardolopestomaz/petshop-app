package dev.ricardolptz.Petshop.DTO;

import dev.ricardolptz.Petshop.model.Pet;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PetCreateDTO {
    private String nome;
    private Pet.TipoPet tipo;
    private String raca;
    private Integer idade;
    private Double peso;
    private Long usuarioId;
}