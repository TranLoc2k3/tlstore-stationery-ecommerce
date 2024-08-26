package com.tl.tlstore.tlstore.service.impl;

import com.tl.tlstore.tlstore.model.Order;
import com.tl.tlstore.tlstore.repository.OrderRepository;
import com.tl.tlstore.tlstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Order> findAllByUserUsername(String username) {
        return orderRepository.findAllByUserUsername(username);
    }

    @Override
    public Order updateStatus(Long orderId, String status) {
        int updated = orderRepository.updateStatusById(orderId, status);
        int updatedTime = orderRepository.updateLastChangeTimeById(orderId);
        if (updated == 1 && updatedTime == 1) {
            return orderRepository.findById(orderId).get();
        }
        return null;
    }

    @Override
    public List<Order> findAllByStatus(String status) {
        return orderRepository.findAllByStatus(status);
    }
}
