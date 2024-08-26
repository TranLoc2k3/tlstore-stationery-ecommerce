package com.tl.tlstore.tlstore.controller;

import com.tl.tlstore.tlstore.model.*;
import com.tl.tlstore.tlstore.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class CartController {

    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping("/cart")
    public String showCart(HttpSession session, Model model) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart != null) {
            Map<Long, Integer> products = cart.getProducts();
            List<Product> productList = productService.findAllById(products.keySet());
            model.addAttribute("products", productList);
            model.addAttribute("quantities", products);
        }
        return "ecommerce/cart";
    }

    @GetMapping("/add-to-cart")
    @ResponseBody
    public ResponseEntity<?> addToCart(@RequestParam("productId") Long productId,
                            @RequestParam("quantity") int quantity,
                            HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
        }
        cart.addProduct(productId, quantity);
        session.setAttribute("cart", cart);
        return ResponseEntity.ok().body("Product added successfuly");
    }

    @GetMapping("/update-quantity")
    @ResponseBody
    public ResponseEntity<?> updateQuantity(@RequestParam("productId") Long productId,
                            @RequestParam("quantity") int quantity,
                            HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart != null) {
            cart.updateQuantity(productId, quantity);
            session.setAttribute("cart", cart);
        }
        return ResponseEntity.ok().body("Quantity updated successfuly");
    }

    @GetMapping("/checkout")
    public String checkout(HttpSession session) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.getUserByEmail(username);
        Cart cart = (Cart) session.getAttribute("cart");

        // Check if user has address and name
        if (user != null) {
            if (user.getAddress() == null || user.getFirstName() == null || user.getLastName() == null) {
                return "redirect:/profile";
            }
        }

        Map<Long, Integer> products = cart.getProducts();
        List<Product> productList = productService.findAllById(products.keySet());

        Address addressUser = user.getAddress();
        Address addressOrder = new Address();

        addressOrder.setProvince(addressUser.getProvince());
        addressOrder.setDistrict(addressUser.getDistrict());
        addressOrder.setWard(addressUser.getWard());
        addressOrder.setAddress_detail(addressUser.getAddress_detail());
        Address savedAddressOrder = addressService.save(addressOrder);

        Order order = new Order();
        order.setUser(user);
        order.setAddress(savedAddressOrder);
        order.setStatus("pending");
        order.setDate(java.time.LocalDateTime.now());
        double totalPrice = 0;
        for (Product product : productList) {
            totalPrice += product.getPrice() * products.get(product.getId());
        }
        order.setTotalPrice(totalPrice);
        Order savedOrder =  orderService.save(order);

        productList.forEach(product -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(savedOrder);
            orderDetail.setProduct(product);
            orderDetail.setQuantity(products.get(product.getId()));
            orderDetail.setPrice(product.getPrice());
            orderDetailService.save(orderDetail);
        });
        return "redirect:/order";
    }
}
