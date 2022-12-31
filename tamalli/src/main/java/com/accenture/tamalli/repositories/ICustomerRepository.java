package com.accenture.tamalli.repositories;

import com.accenture.tamalli.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface ICustomerRepository extends JpaRepository<Customer,Long> {

}
