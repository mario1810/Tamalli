package com.accenture.tamalli.repositories;

import com.accenture.tamalli.models.Drink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDrinkRepository extends JpaRepository<Drink, Long> {
}
