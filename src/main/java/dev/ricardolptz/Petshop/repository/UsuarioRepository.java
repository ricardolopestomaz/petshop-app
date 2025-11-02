package dev.ricardolptz.Petshop.repository;

import dev.ricardolptz.Petshop.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
