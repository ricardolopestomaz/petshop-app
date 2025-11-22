package dev.ricardolptz.Petshop.controller;

import dev.ricardolptz.Petshop.DTO.UsuarioResponseDTO;
import dev.ricardolptz.Petshop.DTO.UsuarioUpdateDTO;
import dev.ricardolptz.Petshop.model.Usuario;
import dev.ricardolptz.Petshop.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
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

    @PatchMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> updateUsuario(
            @PathVariable Long id,
            @RequestBody UsuarioUpdateDTO dto) {
        return ResponseEntity.ok(usuarioService.updateUsuario(id, dto));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){usuarioService.delete(id);}
}
