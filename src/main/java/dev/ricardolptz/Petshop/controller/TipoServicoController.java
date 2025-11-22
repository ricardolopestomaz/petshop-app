package dev.ricardolptz.Petshop.controller;

import dev.ricardolptz.Petshop.DTO.TipoServicoDTO;
import dev.ricardolptz.Petshop.DTO.TipoServicoUpdateDTO;
import dev.ricardolptz.Petshop.model.TipoServico;
import dev.ricardolptz.Petshop.service.TipoServicoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipo-servico")
public class TipoServicoController {

    private final TipoServicoService tipoServicoService;

    public TipoServicoController(TipoServicoService tipoServicoService) {
        this.tipoServicoService = tipoServicoService;
    }

    @GetMapping
    public List<TipoServicoDTO> getAll() {
        return tipoServicoService.getAll();
    }

    @PostMapping
    public TipoServicoDTO create(@RequestBody TipoServico tipoServico) {
        return tipoServicoService.save(tipoServico);
    }

    @PutMapping("/{id}")
    public TipoServicoDTO update(@PathVariable Long id, @RequestBody TipoServicoUpdateDTO dto) {
        return tipoServicoService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        tipoServicoService.delete(id);
    }
}
