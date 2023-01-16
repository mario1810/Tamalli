package com.accenture.tamalli.repositories;

import com.accenture.tamalli.models.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface IOrderDetailRepository extends JpaRepository<OrderDetail,Long> {
    List<OrderDetail> findByProductIsNullAndOrderPaidFalse();
    List<OrderDetail> findByProductProductId(Long productId);

    boolean existsByProductProductIdAndOrderOrderId(Long productId,Long orderId);
    Optional<OrderDetail> findByDetailOrderId(Long id);

}
