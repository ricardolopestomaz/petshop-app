package com.petshop.api.model.tipo_animal;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.petshop.api.model.animal.AnimalModel;
import com.petshop.api.model.produto.ProdutoModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tipo_animal")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TipoAnimalModel {

    public enum Tipo {
        CACHORRO,
        GATO,
        PASSARO,
        PEIXE,
        OUTROS_PETS
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    // Um Tipo pode ter varios animais
    @OneToMany(mappedBy = "tipoAnimal")
    @JsonManagedReference
    private List<AnimalModel> animais;

    // Muitos tipos podem ter muitos produtos
    @ManyToMany(mappedBy = "tipo_animais")
    @JsonBackReference
    private Set<ProdutoModel> produtos = new HashSet<>();
}
