package dev.ricardolptz.Petshop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pet_table")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String nome;

    private Integer idade;

    @Column(length = 50)
    private String raca;

    private double peso;

    //RELACIONAMENTOS

    //PET - USUÁRIO
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonBackReference("usuario-pet")
    private Usuario usuario;

    //PET - TIPO_PET
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_pet_id", nullable = false)
    @JsonBackReference("tipo-pet")
    private TipoPet tipoPet;
}
