package dev.ricardolptz.Petshop.controller;

import dev.ricardolptz.Petshop.model.TipoPet;
import dev.ricardolptz.Petshop.service.TipoPetService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tipo-pet")
public class TipoPetController {
    private final TipoPetService tipoPetService;

    public TipoPetController(TipoPetService tipoPetService) {
        this.tipoPetService = tipoPetService;
    }

    @GetMapping
    public List<TipoPet> getAll(){return tipoPetService.getAll();}
}
