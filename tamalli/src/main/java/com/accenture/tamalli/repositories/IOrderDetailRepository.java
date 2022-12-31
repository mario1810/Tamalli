package com.accenture.tamalli.repositories;

import com.accenture.tamalli.models.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderDetailRepository extends JpaRepository<OrderDetail,Long> {
}
