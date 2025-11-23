package dev.ricardolptz.Petshop.controller;

import dev.ricardolptz.Petshop.DTO.UsuarioCreateDTO;
import dev.ricardolptz.Petshop.DTO.UsuarioResponseDTO;
import dev.ricardolptz.Petshop.DTO.UsuarioUpdateDTO;
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
    public ResponseEntity<UsuarioResponseDTO> create(@RequestBody UsuarioCreateDTO dto) {
        return ResponseEntity.ok(usuarioService.create(dto));
    }

    @PutMapping("/{id}") // Garanta que é @PutMapping
    public ResponseEntity<UsuarioResponseDTO> update(@PathVariable Long id, @RequestBody UsuarioUpdateDTO dto) {
        UsuarioResponseDTO atualizado = usuarioService.updateUsuario(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> updateUsuario(
            @PathVariable Long id,
            @RequestBody UsuarioUpdateDTO dto) {
        return ResponseEntity.ok(usuarioService.updateUsuario(id, dto));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){usuarioService.delete(id);}
}
