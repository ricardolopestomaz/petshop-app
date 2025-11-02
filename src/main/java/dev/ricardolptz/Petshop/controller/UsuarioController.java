package dev.ricardolptz.Petshop.controller;

import dev.ricardolptz.Petshop.DTO.UsuarioResponseDTO;
import dev.ricardolptz.Petshop.model.Usuario;
import dev.ricardolptz.Petshop.service.UsuarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<UsuarioResponseDTO> getAll(){return usuarioService.getAll();}

    @PostMapping
    public UsuarioResponseDTO create(@RequestBody Usuario usuario){return usuarioService.save(usuario);}

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){usuarioService.delete(id);}
}
