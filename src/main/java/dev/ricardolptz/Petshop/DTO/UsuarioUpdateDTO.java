package dev.ricardolptz.Petshop.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioUpdateDTO {
    private String nome;
    private String email;
    private String telefone;
}
