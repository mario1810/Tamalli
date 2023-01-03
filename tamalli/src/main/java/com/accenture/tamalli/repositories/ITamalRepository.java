package com.accenture.tamalli.repositories;

import com.accenture.tamalli.models.Tamal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface ITamalRepository extends JpaRepository<Tamal,Long> {
}
