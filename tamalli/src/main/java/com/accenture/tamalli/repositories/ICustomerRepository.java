package com.accenture.tamalli.repositories;

import com.accenture.tamalli.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer,Long> {

    List<Customer> findAll();
    Optional<Customer> findByCustomerId(Long id);
    Optional<Customer> findFistByEmailAndPassword(String email, String password);
    Optional<Customer> findFistByEmail(String email);

}
