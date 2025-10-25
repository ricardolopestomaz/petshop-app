package com.petshop.api.repository.categoria_produto;

import com.petshop.api.model.categoria_produto.CategoriaProdutoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaProdutoRepository extends JpaRepository<CategoriaProdutoModel, Long> {
}
