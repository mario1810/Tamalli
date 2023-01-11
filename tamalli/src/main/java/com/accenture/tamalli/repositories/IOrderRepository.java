package com.accenture.tamalli.repositories;


import com.accenture.tamalli.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface IOrderRepository extends JpaRepository<Order,Long> {

    // findBy[field's name at order entity][field's name at Customer entity]And[field at order entity]
    Optional<Order> findFirstByCustomerCustomerIdAndPaidFalse(Long customerId);
    List<Order> findByPaidTrue();
    List<Order> findByPaidTrueAndPurchaseDateAfter(LocalDateTime date);

    Boolean existsByOrderId(long orderId);
}
