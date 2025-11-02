package dev.ricardolptz.Petshop.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tipo_animal")
@NoArgsConstructor
@AllArgsConstructor
public class TipoPet {
    public enum Tipo {
        CACHORRO,
        GATO,
        PASSARO,
        PEIXE,
        OUTROS_PETS
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    //RELACIONAMETOS

    //TIPO_PET - PET
    @OneToMany(
            mappedBy = "tipoPet",
            fetch = FetchType.LAZY
    )
    @JsonManagedReference("tipo-pet")
    private List<Pet> pets = new ArrayList<>();
}
