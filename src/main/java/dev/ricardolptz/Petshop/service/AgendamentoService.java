package dev.ricardolptz.Petshop.service;

import dev.ricardolptz.Petshop.DTO.AgendamentoCreateDTO;
import dev.ricardolptz.Petshop.DTO.AgendamentoDTO;
import dev.ricardolptz.Petshop.DTO.AgendamentoUpdateDTO;
import dev.ricardolptz.Petshop.model.Agendamento;
import dev.ricardolptz.Petshop.model.Pet;
import dev.ricardolptz.Petshop.model.Servico;
import dev.ricardolptz.Petshop.repository.AgendamentoRepository;
import dev.ricardolptz.Petshop.repository.PetRepository;
import dev.ricardolptz.Petshop.repository.ServicoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final PetRepository petRepository;
    private final ServicoRepository servicoRepository;

    public AgendamentoService(AgendamentoRepository agendamentoRepository,
                              PetRepository petRepository,
                              ServicoRepository servicoRepository) {
        this.agendamentoRepository = agendamentoRepository;
        this.petRepository = petRepository;
        this.servicoRepository = servicoRepository;
    }

    @Transactional(readOnly = true)
    public List<AgendamentoDTO> getAll() {
        return agendamentoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public AgendamentoDTO create(AgendamentoCreateDTO dto) {
        // 1. VALIDAÇÃO: Verifica se o horário está ocupado (ignorando cancelados)
        if (agendamentoRepository.existsByDataHoraAndStatusNot(dto.getDataHora(), Agendamento.Status.CANCELADO)) {
            throw new RuntimeException("Horário indisponível! Já existe agendamento para: " + dto.getDataHora());
        }

        Agendamento agendamento = new Agendamento();
        agendamento.setDataHora(dto.getDataHora());
        agendamento.setObservacoes(dto.getObservacoes());
        // O status já nasce como PENDENTE automaticamente

        // 2. Busca as entidades (Pet e Serviço)
        Pet pet = petRepository.findById(dto.getPetId())
                .orElseThrow(() -> new RuntimeException("Pet não encontrado"));
        Servico servico = servicoRepository.findById(dto.getServicoId())
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        agendamento.setPet(pet);
        agendamento.setServico(servico);

        Agendamento salvo = agendamentoRepository.save(agendamento);
        return toDTO(salvo);
    }

    @Transactional
    public AgendamentoDTO update(Long id, AgendamentoUpdateDTO dto) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));

        // Validação se a pessoa tentar mudar o horário para um já ocupado
        if (dto.getDataHora() != null && !dto.getDataHora().equals(agendamento.getDataHora())) {
            if (agendamentoRepository.existsByDataHoraAndStatusNot(dto.getDataHora(), Agendamento.Status.CANCELADO)) {
                throw new RuntimeException("O novo horário escolhido está indisponível!");
            }
            agendamento.setDataHora(dto.getDataHora());
        }

        if (dto.getStatus() != null) agendamento.setStatus(dto.getStatus());
        if (dto.getObservacoes() != null) agendamento.setObservacoes(dto.getObservacoes());

        Agendamento atualizado = agendamentoRepository.save(agendamento);
        return toDTO(atualizado);
    }

    @Transactional
    public void delete(Long id) {
        agendamentoRepository.deleteById(id);
    }

    // Converte Entidade -> DTO de Resposta
    private AgendamentoDTO toDTO(Agendamento agendamento) {
        AgendamentoDTO dto = new AgendamentoDTO();
        dto.setId(agendamento.getId());
        dto.setDataHora(agendamento.getDataHora());
        dto.setStatus(agendamento.getStatus());
        dto.setObservacoes(agendamento.getObservacoes());

        if (agendamento.getPet() != null) {
            dto.setPetId(agendamento.getPet().getId());
            dto.setNomePet(agendamento.getPet().getNome());

            if(agendamento.getPet().getUsuario() != null) {
                dto.setNomeDono(agendamento.getPet().getUsuario().getNome());
            }
        }

        if (agendamento.getServico() != null) {
            dto.setServicoId(agendamento.getServico().getId());
            dto.setNomeServico(agendamento.getServico().getNome());
            dto.setPreco(agendamento.getServico().getPreco());
        }

        return dto;
    }
}