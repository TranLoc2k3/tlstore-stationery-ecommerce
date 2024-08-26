package com.tl.tlstore.tlstore.service.impl;

import com.tl.tlstore.tlstore.model.OrderDetail;
import com.tl.tlstore.tlstore.repository.OrderDetailRepository;
import com.tl.tlstore.tlstore.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Override
    public OrderDetail save(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }
}
