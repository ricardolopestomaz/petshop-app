package dev.ricardolptz.Petshop.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "servico")
public class Servico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 60)
    private String nome;

    private String descricao;

    @Column(nullable = false)
    private double preco;

    private Integer duracaoMinutos;

    @Column(nullable = false)
    private boolean ativo = true;

    // CORREÇÃO AQUI: Trocamos @Enumerated por @ManyToOne
    @ManyToOne
    @JoinColumn(name = "tipo_servico_id")
    private TipoServico tipoServico;

    @OneToMany(mappedBy = "servico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Agendamento> agendamentos;
}