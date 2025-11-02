package dev.ricardolptz.Petshop.service;

import dev.ricardolptz.Petshop.model.TipoPet;
import dev.ricardolptz.Petshop.repository.TipoPetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoPetService {

    private final TipoPetRepository tipoPetRepository;

    public TipoPetService(TipoPetRepository tipoPetRepository) {
        this.tipoPetRepository = tipoPetRepository;
    }

    public List<TipoPet> getAll(){return tipoPetRepository.findAll();}
}
