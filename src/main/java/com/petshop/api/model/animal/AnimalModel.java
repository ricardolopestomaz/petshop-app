package com.petshop.api.model.animal;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.petshop.api.model.agendamento.AgendamentoModel;
import com.petshop.api.model.tipo_animal.TipoAnimalModel;
import com.petshop.api.model.usuario.UsuarioModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "animal")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AnimalModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String nome;

    private int idade;

    @Column(length = 50)
    private String raca;

    private double peso;

    // Muitos animais pertencem a um usuário
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonBackReference
    private UsuarioModel usuario;

    // Muitos animais pertencem a um tipo de animal (ex: CACHORRO, GATO...)
    @ManyToOne
    @JoinColumn(name = "tipo_animal_id", nullable = false)
    @JsonBackReference
    private TipoAnimalModel tipoAnimal;

    // Um animal pode ter vários agendamentos
    @OneToMany(mappedBy = "animal")
    @JsonManagedReference
    private List<AgendamentoModel> agendamentos;
}
