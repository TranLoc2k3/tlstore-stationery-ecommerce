package com.tl.tlstore.tlstore.service;

import com.tl.tlstore.tlstore.model.Order;
import org.springframework.stereotype.Service;

import java.util.List;

public interface OrderService {
    Order findById(Long id);
    Order save(Order order);
    List<Order> findAllByUserUsername(String username);
    Order updateStatus(Long orderId, String status);

    List<Order> findAllByStatus(String status);
}
