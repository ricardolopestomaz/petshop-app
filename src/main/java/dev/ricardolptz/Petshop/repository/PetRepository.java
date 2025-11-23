package dev.ricardolptz.Petshop.repository;

import dev.ricardolptz.Petshop.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
}
