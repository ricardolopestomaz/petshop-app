package com.petshop.api.model.categoria_produto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.petshop.api.model.produto.ProdutoModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categoria_produto")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoriaProdutoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50, nullable = false, unique = true)
    private String nome;

    // Muitas categorias podem ter muitos produtos
    @ManyToMany(mappedBy = "categorias")
    @JsonBackReference
    private Set<ProdutoModel> produtos = new HashSet<>();
}
