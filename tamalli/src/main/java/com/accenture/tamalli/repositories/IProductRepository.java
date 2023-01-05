package com.accenture.tamalli.repositories;
import com.accenture.tamalli.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface IProductRepository extends JpaRepository<Product,Long> {
    Optional<Product> findByProductId(Long productId);
    List<Product> findAll();
}
