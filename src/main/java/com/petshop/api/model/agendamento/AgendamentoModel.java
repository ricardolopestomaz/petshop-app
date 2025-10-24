package com.petshop.api.model.agendamento;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.petshop.api.model.animal.AnimalModel;
import com.petshop.api.model.servico.ServicoModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;


@Entity
@Table(name = "agendamento")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AgendamentoModel {

    public enum Status {
        PENDENTE,
        CONFIRMADO,
        CANCELADO,
        CONCLUIDO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private LocalTime horario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.PENDENTE;

    // Muitos agendamentos podem ter um animal
    @ManyToOne
    @JoinColumn(name = "animal_id", nullable = false)
    @JsonBackReference
    private AnimalModel animal;

    // Muitos agendamentos podem ter um servico
    @ManyToOne
    @JoinColumn(name = "servico_id", nullable = false)
    @JsonBackReference
    private ServicoModel servico;

}
