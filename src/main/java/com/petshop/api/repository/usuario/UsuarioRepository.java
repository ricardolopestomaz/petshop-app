package com.petshop.api.repository.usuario;

import com.petshop.api.model.usuario.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional; // Importe o Optional

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {

    List<UsuarioModel> findByNomeContaining(String nome);

    // boolean findByEmail(String email); // <-- REMOVA ESTA LINHA (ou corrija)

    // Este é usado para verificar duplicados no cadastro
    boolean existsByEmail(String email);

    // Este é o método CORRETO que o seu 'verificarLogin' precisa
    Optional<UsuarioModel> findByEmail(String email);
}