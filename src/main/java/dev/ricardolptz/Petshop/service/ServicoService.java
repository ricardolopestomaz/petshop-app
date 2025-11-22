package dev.ricardolptz.Petshop.service;

import dev.ricardolptz.Petshop.DTO.ServicoDTO;
import dev.ricardolptz.Petshop.DTO.ServicoUpdateDTO;
import dev.ricardolptz.Petshop.model.Servico;
import dev.ricardolptz.Petshop.model.TipoServico;
import dev.ricardolptz.Petshop.repository.ServicoRepository;
import dev.ricardolptz.Petshop.repository.TipoServicoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicoService {

    private final ServicoRepository servicoRepository;
    private final TipoServicoRepository tipoServicoRepository;

    public ServicoService(ServicoRepository servicoRepository, TipoServicoRepository tipoServicoRepository) {
        this.servicoRepository = servicoRepository;
        this.tipoServicoRepository = tipoServicoRepository;
    }

    @Transactional(readOnly = true)
    public List<ServicoDTO> getAll() {
        return servicoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ServicoDTO save(Servico servico) {
        Servico salvo = servicoRepository.save(servico);
        return toDTO(salvo);
    }

    @Transactional
    // Agora recebe ServicoUpdateDTO em vez de ServicoDTO
    public ServicoDTO update(Long id, ServicoUpdateDTO dto) {
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        // Atualiza apenas se o campo não for nulo
        if (dto.getNome() != null) servico.setNome(dto.getNome());
        if (dto.getDescricao() != null) servico.setDescricao(dto.getDescricao());

        // Verifica se o preço foi enviado (assumindo que 0 ou null não altera)
        if (dto.getPreco() != null) servico.setPreco(dto.getPreco());

        if (dto.getDuracaoMinutos() != null) servico.setDuracaoMinutos(dto.getDuracaoMinutos());
        if (dto.getAtivo() != null) servico.setAtivo(dto.getAtivo());

        // Lógica corrigida do relacionamento
        if (dto.getTipoServicoId() != null) {
            TipoServico tipo = tipoServicoRepository.findById(dto.getTipoServicoId())
                    .orElseThrow(() -> new RuntimeException("TipoServico não encontrado"));
            servico.setTipoServico(tipo);
        }

        Servico atualizado = servicoRepository.save(servico);
        return toDTO(atualizado);
    }

    @Transactional
    public void delete(Long id) {
        servicoRepository.deleteById(id);
    }

    private ServicoDTO toDTO(Servico servico) {
        ServicoDTO dto = new ServicoDTO();
        dto.setId(servico.getId());
        dto.setNome(servico.getNome());
        dto.setDescricao(servico.getDescricao());
        dto.setPreco(servico.getPreco());
        dto.setDuracaoMinutos(servico.getDuracaoMinutos());
        dto.setAtivo(servico.isAtivo());

        // Lógica para preencher os dados do Tipo de Serviço
        if (servico.getTipoServico() != null) {
            dto.setTipoServicoId(servico.getTipoServico().getId());
            dto.setNomeTipoServico(servico.getTipoServico().getNome());
        }

        return dto;
    }
}
