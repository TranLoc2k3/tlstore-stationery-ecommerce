package com.tl.tlstore.tlstore.repository;

import com.tl.tlstore.tlstore.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUserUsername(String username);
    @Modifying
    @Transactional
    @Query("UPDATE Order o SET o.status = :status WHERE o.id = :orderId")
    int updateStatusById(Long orderId, String status);

    @Modifying
    @Transactional
    @Query("UPDATE Order o SET o.lastUpdate = CURRENT_TIMESTAMP WHERE o.id = :orderId")
    int updateLastChangeTimeById(Long orderId);

    List<Order> findAllByStatus(String status);
}