package com.accenture.tamalli.repositories;

import com.accenture.tamalli.models.Drink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface IDrinkRepository extends JpaRepository<Drink, Long> {
}
