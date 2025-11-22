package dev.ricardolptz.Petshop.service;

import dev.ricardolptz.Petshop.DTO.TipoServicoDTO;
import dev.ricardolptz.Petshop.DTO.TipoServicoUpdateDTO;
import dev.ricardolptz.Petshop.model.TipoServico;
import dev.ricardolptz.Petshop.repository.TipoServicoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TipoServicoService {

    private final TipoServicoRepository tipoServicoRepository;

    public TipoServicoService(TipoServicoRepository tipoServicoRepository) {
        this.tipoServicoRepository = tipoServicoRepository;
    }

    @Transactional(readOnly = true)
    public List<TipoServicoDTO> getAll() {
        return tipoServicoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public TipoServicoDTO save(TipoServico tipoServico) {
        TipoServico salvo = tipoServicoRepository.save(tipoServico);
        return toDTO(salvo);
    }

    @Transactional
    // Altere o parâmetro para TipoServicoUpdateDTO
    public TipoServicoDTO update(Long id, TipoServicoUpdateDTO dto) {
        TipoServico tipoServico = tipoServicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TipoServico não encontrado"));

        // Atualiza apenas se não for nulo
        if (dto.getNome() != null) tipoServico.setNome(dto.getNome());
        if (dto.getDescricao() != null) tipoServico.setDescricao(dto.getDescricao());

        // Para booleanos wrappers (Boolean), verificamos null
        if (dto.getAtivo() != null) tipoServico.setAtivo(dto.getAtivo());

        TipoServico atualizado = tipoServicoRepository.save(tipoServico);
        return toDTO(atualizado); // Reaproveita seu método privado toDTO existente
    }

    @Transactional
    public void delete(Long id) {
        tipoServicoRepository.deleteById(id);
    }

    private TipoServicoDTO toDTO(TipoServico tipoServico) {
        TipoServicoDTO dto = new TipoServicoDTO();
        dto.setId(tipoServico.getId());
        dto.setNome(tipoServico.getNome());
        dto.setDescricao(tipoServico.getDescricao());
        dto.setAtivo(tipoServico.isAtivo());
        return dto;
    }
}
