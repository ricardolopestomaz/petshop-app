package dev.ricardolptz.Petshop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pet")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, nullable = false)
    private String nome;
    private Integer idade;
    @Column(length = 50)
    private String raca;
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private TipoPet tipo;
    private double peso;

    //PET - USUÁRIO
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonBackReference("usuario-pet")
    private Usuario usuario;

    public enum TipoPet {
        CACHORRO,
        GATO,
        PASSARO,
        PEIXE,
        OUTRO
    }
}
