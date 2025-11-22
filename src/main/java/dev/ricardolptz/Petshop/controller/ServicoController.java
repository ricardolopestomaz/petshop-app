package dev.ricardolptz.Petshop.controller;

import dev.ricardolptz.Petshop.DTO.ServicoDTO;
import dev.ricardolptz.Petshop.DTO.ServicoUpdateDTO;
import dev.ricardolptz.Petshop.model.Servico;
import dev.ricardolptz.Petshop.service.ServicoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servico")
public class ServicoController {

    private final ServicoService servicoService;

    public ServicoController(ServicoService servicoService) {
        this.servicoService = servicoService;
    }

    @GetMapping
    public List<ServicoDTO> getAll() {
        return servicoService.getAll();
    }

    @PostMapping
    public ServicoDTO create(@RequestBody Servico servico) {
        return servicoService.save(servico);
    }

    @PutMapping("/{id}")
    public ServicoDTO update(@PathVariable Long id, @RequestBody ServicoUpdateDTO dto) {
        return servicoService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        servicoService.delete(id);
    }
}
