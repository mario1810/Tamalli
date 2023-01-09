package com.accenture.tamalli.repositories;

import com.accenture.tamalli.models.Drink;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;


public interface IDrinkRepository extends JpaRepository<Drink, Long> {
    Optional<Drink> findByProductId(Long drinkId);

    List<Drink> findByProductNameIgnoreCaseAndCapacityLiters(String productName, double capacity);
    List<Drink> findAll();

    Boolean existsByProductId(Long id);
}
