package com.tl.tlstore.tlstore.repository;

import com.tl.tlstore.tlstore.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}