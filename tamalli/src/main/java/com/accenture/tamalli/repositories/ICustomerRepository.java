package com.accenture.tamalli.repositories;

import com.accenture.tamalli.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ICustomerRepository extends JpaRepository<Customer,Long> {

    List<Customer> findAll();
    Optional<Customer> findByCustomerId(Long id);
    Optional<Customer> findFirstByEmailAndPassword(String email, String password);
    Optional<Customer> findFirstByEmail(String email);
    boolean existsByEmail(String email);

}
