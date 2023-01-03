package com.accenture.tamalli.repositories;

import com.accenture.tamalli.models.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface IOrderDetailRepository extends JpaRepository<OrderDetail,Long> {
}
