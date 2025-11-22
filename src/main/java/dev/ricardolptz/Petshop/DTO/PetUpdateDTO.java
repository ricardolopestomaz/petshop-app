package dev.ricardolptz.Petshop.DTO;

import dev.ricardolptz.Petshop.model.Pet;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PetUpdateDTO {
    private String nome;
    private Integer idade;
    private String raca;
    private Double peso;
    private Pet.TipoPet tipo;
}
