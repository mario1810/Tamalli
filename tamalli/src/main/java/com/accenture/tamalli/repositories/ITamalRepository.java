package com.accenture.tamalli.repositories;
import com.accenture.tamalli.models.Tamal;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;


public interface ITamalRepository extends JpaRepository<Tamal,Long> {
    Optional<Tamal> findByProductId(Long tamalId);
    List<Tamal> findAll();
}
