package com.accenture.tamalli.repositories;

import com.accenture.tamalli.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductRepository extends JpaRepository<Product,Long> {
}
