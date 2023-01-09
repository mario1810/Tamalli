package com.accenture.tamalli.repositories;


import com.accenture.tamalli.models.ProductDescription;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface IProductDescriptionRepository extends MongoRepository<ProductDescription,Long> {

    Optional<ProductDescription> findByProductId(Long productId);

    Boolean existsByProductId(Long productId);



}
