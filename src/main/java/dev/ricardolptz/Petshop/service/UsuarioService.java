package dev.ricardolptz.Petshop.service;

import dev.ricardolptz.Petshop.DTO.PetResponseDTO;
import dev.ricardolptz.Petshop.DTO.TipoPetResponseDTO;
import dev.ricardolptz.Petshop.DTO.UsuarioResponseDTO;
import dev.ricardolptz.Petshop.model.Pet;
import dev.ricardolptz.Petshop.model.Usuario;
import dev.ricardolptz.Petshop.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    //MÉTODOS
    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> getAll(){
        return usuarioRepository.findAll()
                .stream()
                .map(this::toUsuarioResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public UsuarioResponseDTO save(Usuario usuario){
        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        return toUsuarioResponseDTO(usuarioSalvo);
    }

    private UsuarioResponseDTO toUsuarioResponseDTO(Usuario entidade){
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(entidade.getId());
        dto.setNome(entidade.getNome());
        dto.setEmail(entidade.getEmail());

        if(entidade.getPets() != null){
            List<PetResponseDTO> petsDTO = entidade.getPets().stream()
                    .map(this::toPetResponseDTO)
                    .collect(Collectors.toList());
            dto.setPets(petsDTO);

        }
        return dto;
    }

    private PetResponseDTO toPetResponseDTO(Pet petEntidade){
        PetResponseDTO petDTO = new PetResponseDTO();
        petDTO.setId(petEntidade.getId());
        petDTO.setNome(petEntidade.getNome());
        petDTO.setRaca(petEntidade.getRaca());
        petDTO.setIdade(petEntidade.getIdade());
        petDTO.setPeso(petEntidade.getPeso());

        if(petEntidade.getTipoPet() != null && petEntidade.getTipoPet().getTipo() != null){
            TipoPetResponseDTO tipoDTO = new TipoPetResponseDTO(
                    petEntidade.getTipoPet().getId(),
                    petEntidade.getTipoPet().getTipo().name()
            );
            petDTO.setTipoPet(tipoDTO);
        }
        return  petDTO;
    }

    public void delete(Long id){usuarioRepository.deleteById(id);}
}
