package com.tl.tlstore.tlstore.controller;

import com.tl.tlstore.tlstore.model.Order;
import com.tl.tlstore.tlstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/order")
    public String order(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<Order> orders = orderService.findAllByUserUsername(username);
        List<Order> pending_orders = new ArrayList<>();
        List<Order> shipping_orders = new ArrayList<>();
        List<Order> delivered_orders = new ArrayList<>();
        List<Order> cancelled_orders = new ArrayList<>();

        orders.forEach(order -> {
            switch (order.getStatus()) {
                case "pending":
                    pending_orders.add(order);
                    break;
                case "shipping":
                    shipping_orders.add(order);
                    break;
                case "delivered":
                    delivered_orders.add(order);
                    break;
                case "cancelled":
                    cancelled_orders.add(order);
                    break;
            }
        });
        model.addAttribute("pending_orders", pending_orders);
        model.addAttribute("shipping_orders", shipping_orders);
        model.addAttribute("delivered_orders", delivered_orders);
        model.addAttribute("cancelled_orders", cancelled_orders);

        return "ecommerce/order";
    }

    @PutMapping("/order/update-status")
    public String updateStatus(@RequestParam Long orderId, @RequestParam String status) {
        orderService.updateStatus(orderId, status);
        return "redirect:/order";
    }

}
