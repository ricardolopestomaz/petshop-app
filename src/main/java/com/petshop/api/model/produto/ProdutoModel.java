package com.petshop.api.model.produto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.petshop.api.model.categoria_produto.CategoriaProdutoModel;
import com.petshop.api.model.tipo_animal.TipoAnimalModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "produto")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProdutoModel {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, nullable = false)
    private String nome;
    @Column(nullable = false)
    private double preco;
    @Column(length = 255, nullable = false)
    private String descricao;
    @Column(length = 255, nullable = false)
    private String imagem;
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    boolean ativo;

    // Muitos Produtos podem ter muitas categorias
    @ManyToMany
    @JoinTable(
            name = "produto_categoria",
            joinColumns = @JoinColumn(name = "produto_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    @JsonManagedReference
    private Set<CategoriaProdutoModel> categorias = new HashSet<>();

    // Muitos Produtos podem ter muitos animais
@ManyToMany
    @JoinTable(
            name = "produto_tipo_animal",
            joinColumns = @JoinColumn(name = "produto_id"),
            inverseJoinColumns = @JoinColumn(name = "tipo_animal_id")
    )
    @JsonManagedReference
    private Set<TipoAnimalModel> tipo_animais = new HashSet<>();
}
