package dev.ricardolptz.Petshop.controller;

import dev.ricardolptz.Petshop.DTO.AgendamentoCreateDTO;
import dev.ricardolptz.Petshop.DTO.AgendamentoDTO;
import dev.ricardolptz.Petshop.DTO.AgendamentoUpdateDTO;
import dev.ricardolptz.Petshop.service.AgendamentoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agendamento")
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    public AgendamentoController(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }

    @GetMapping
    public List<AgendamentoDTO> getAll() {
        return agendamentoService.getAll();
    }

    @PostMapping
    public AgendamentoDTO create(@RequestBody AgendamentoCreateDTO dto) {
        return agendamentoService.create(dto);
    }

    @PutMapping("/{id}")
    public AgendamentoDTO update(@PathVariable Long id, @RequestBody AgendamentoUpdateDTO dto) {
        return agendamentoService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        agendamentoService.delete(id);
    }
}