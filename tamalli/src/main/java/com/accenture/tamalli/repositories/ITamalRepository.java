package com.accenture.tamalli.repositories;

import com.accenture.tamalli.models.Tamal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITamalRepository extends JpaRepository<Tamal,Long> {
}
