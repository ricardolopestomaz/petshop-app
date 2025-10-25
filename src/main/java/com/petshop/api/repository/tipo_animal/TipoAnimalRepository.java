package com.petshop.api.repository.tipo_animal;

import com.petshop.api.model.tipo_animal.TipoAnimalModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoAnimalRepository extends JpaRepository<TipoAnimalModel, Long> {
}
