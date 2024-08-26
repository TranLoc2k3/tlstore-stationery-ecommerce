package com.tl.tlstore.tlstore.controller;

import com.tl.tlstore.tlstore.model.*;
import com.tl.tlstore.tlstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class OrderDetailController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/orderDetail")
    public String orderDetail(@RequestParam Long id, Model model) {
        Order order = orderService.findById(id);
        User user = order.getUser();
        Address address = order.getAddress();
        List<OrderDetail> orderDetails = order.getOrderDetails();
        Map<OrderDetail, Product> orderDetailMap = new HashMap<>();
        orderDetails.forEach(orderDetail -> {
            orderDetailMap.put(orderDetail, orderDetail.getProduct());
        });
        model.addAttribute("order", order);
        model.addAttribute("orderDetailMap", orderDetailMap);
        model.addAttribute("user", user);
        model.addAttribute("address", address);
        return "ecommerce/orderDetail";
    }
}


