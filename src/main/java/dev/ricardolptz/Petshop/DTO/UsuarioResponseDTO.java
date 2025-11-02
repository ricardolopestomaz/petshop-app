package dev.ricardolptz.Petshop.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UsuarioResponseDTO {
    private Long id;
    private String nome;
    private String email;

    private List<PetResponseDTO> pets;

}
