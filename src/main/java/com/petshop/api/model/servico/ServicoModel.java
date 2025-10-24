package com.petshop.api.model.servico;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.petshop.api.model.agendamento.AgendamentoModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "servico")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ServicoModel {
    public enum Servico {
        BANHO_E_TOSA,
        CONSULTA_VETERINARIA,
        ADOCAO,
        ADESTRAMENTO,
        PLANO_DE_SAUDE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Servico nome;
    @Column(nullable = false)
    private double preco;
    @Column(name = "duracao_minutos",nullable = false)
    private int duracaoMinutos;

    // Um servico pode ter muitos agendamentos
    @OneToMany(mappedBy = "servico")
    @JsonManagedReference
    private List<AgendamentoModel> agendamentos;
}
