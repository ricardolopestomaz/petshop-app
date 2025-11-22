package dev.ricardolptz.Petshop.service;

import dev.ricardolptz.Petshop.DTO.PetResponseDTO;
import dev.ricardolptz.Petshop.DTO.PetUpdateDTO;
import dev.ricardolptz.Petshop.DTO.UsuarioResumoDTO;
import dev.ricardolptz.Petshop.exception.PetNaoEncontradoException;
import dev.ricardolptz.Petshop.model.Pet;
import dev.ricardolptz.Petshop.repository.PetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetService {

    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Transactional(readOnly = true)
    public List<PetResponseDTO> getAll() {
        return petRepository.findAll()
                .stream()
                .map(this::toPetResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public PetResponseDTO save(Pet pet) {
        Pet petSalvo = petRepository.save(pet);
        return toPetResponseDTO(petSalvo);
    }

    public void delete(Long id) {
        petRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public PetResponseDTO buscarPorId(Long id) {
        Pet petEntidade = petRepository.findById(id)
                .orElseThrow(() -> new PetNaoEncontradoException("Pet com id " + id + " não encontrado!"));

        return toPetResponseDTO(petEntidade);
    }

    public PetResponseDTO updatePartial(Long id, PetUpdateDTO updates) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet não encontrado"));

        if (updates.getNome() != null) pet.setNome(updates.getNome());
        if (updates.getIdade() != null) pet.setIdade(updates.getIdade());
        if (updates.getRaca() != null) pet.setRaca(updates.getRaca());
        if (updates.getPeso() != null) pet.setPeso(updates.getPeso());
        if (updates.getTipo() != null) pet.setTipo(updates.getTipo());

        Pet savedPet = petRepository.save(pet);
        return toPetResponseDTO(savedPet);
    }


    private PetResponseDTO toPetResponseDTO(Pet entidade) {
        PetResponseDTO dto = new PetResponseDTO();
        dto.setId(entidade.getId());
        dto.setNome(entidade.getNome());
        dto.setRaca(entidade.getRaca());
        dto.setIdade(entidade.getIdade());
        dto.setPeso(entidade.getPeso());

        // Tipo agora é só uma String
        dto.setTipo(String.valueOf(entidade.getTipo()));

        // Usuário dono do pet
        if (entidade.getUsuario() != null) {
            UsuarioResumoDTO usuarioDTO = new UsuarioResumoDTO(
                    entidade.getUsuario().getId(),
                    entidade.getUsuario().getNome()
            );
            dto.setUsuario(usuarioDTO);
        }

        return dto;
    }
}
