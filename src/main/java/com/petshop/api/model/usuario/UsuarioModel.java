package com.petshop.api.model.usuario;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.petshop.api.model.animal.AnimalModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Entity
@Table(name = "usuario")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UsuarioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, nullable = false)
    private String nome;
    @Column(length = 150, nullable = false, unique = true)
    private String email;
    @Column(length = 100, nullable = false)
    private String senha;
    @Column(length = 20)
    private String telefone;
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean admin;

    // Um usuario pode ter varios animais
    @OneToMany(mappedBy = "usuario")
    @JsonManagedReference
    private List<AnimalModel> animais;
}
