package com.accenture.tamalli.repositories;

import com.accenture.tamalli.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderRepository extends JpaRepository<Order,Long> {
}
