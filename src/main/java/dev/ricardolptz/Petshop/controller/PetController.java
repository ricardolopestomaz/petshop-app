package dev.ricardolptz.Petshop.controller;

import dev.ricardolptz.Petshop.DTO.PetResponseDTO;
import dev.ricardolptz.Petshop.model.Pet;
import dev.ricardolptz.Petshop.service.PetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pet")
public class PetController {
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping
    public List<PetResponseDTO> getAll(){return petService.getAll();};

    @PostMapping
    public ResponseEntity<PetResponseDTO> create(@RequestBody Pet pet){
        PetResponseDTO petDTO = petService.save(pet);
        return ResponseEntity.status(HttpStatus.CREATED).body(petDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){petService.delete(id);}
}
