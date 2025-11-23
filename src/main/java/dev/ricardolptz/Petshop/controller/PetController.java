package dev.ricardolptz.Petshop.controller;

import dev.ricardolptz.Petshop.DTO.PetCreateDTO;
import dev.ricardolptz.Petshop.DTO.PetResponseDTO;
import dev.ricardolptz.Petshop.DTO.PetUpdateDTO;
import dev.ricardolptz.Petshop.service.PetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/pet")
public class PetController {
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping
    public List<PetResponseDTO> getAll(){
        return petService.getAll();
    }

    @PostMapping
    public ResponseEntity<PetResponseDTO> create(@RequestBody PetCreateDTO dto) {
        // Agora chamamos o método 'create' do service que sabe lidar com o DTO
        PetResponseDTO novoPet = petService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPet);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PetResponseDTO> updatePartial(
            @PathVariable Long id,
            @RequestBody PetUpdateDTO updates) {
        PetResponseDTO updatedPet = petService.updatePartial(id, updates);
        return ResponseEntity.ok(updatedPet);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        petService.delete(id);
    }
}