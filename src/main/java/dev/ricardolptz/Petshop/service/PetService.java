package dev.ricardolptz.Petshop.service;

import dev.ricardolptz.Petshop.DTO.PetCreateDTO;
import dev.ricardolptz.Petshop.DTO.PetResponseDTO;
import dev.ricardolptz.Petshop.DTO.PetUpdateDTO;
import dev.ricardolptz.Petshop.DTO.UsuarioResumoDTO;
import dev.ricardolptz.Petshop.exception.PetNaoEncontradoException;
import dev.ricardolptz.Petshop.model.Pet;
import dev.ricardolptz.Petshop.model.Usuario;
import dev.ricardolptz.Petshop.repository.PetRepository;
import dev.ricardolptz.Petshop.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetService {

    private final PetRepository petRepository;
    private final UsuarioRepository usuarioRepository;

    // CONSTRUTOR ATUALIZADO
    public PetService(PetRepository petRepository, UsuarioRepository usuarioRepository) {
        this.petRepository = petRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly = true)
    public List<PetResponseDTO> getAll() {
        return petRepository.findAll()
                .stream()
                .map(this::toPetResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public PetResponseDTO create(PetCreateDTO dto) {
        Pet pet = new Pet();
        pet.setNome(dto.getNome());
        pet.setTipo(dto.getTipo());
        pet.setRaca(dto.getRaca());
        pet.setIdade(dto.getIdade());
        pet.setPeso(dto.getPeso());

        if (dto.getUsuarioId() != null) {
            Usuario dono = usuarioRepository.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new RuntimeException("Dono não encontrado"));
            pet.setUsuario(dono);
        }

        Pet salvo = petRepository.save(pet);
        return toPetResponseDTO(salvo);
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