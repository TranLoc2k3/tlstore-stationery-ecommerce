package com.tl.tlstore.tlstore.controller;

import com.tl.tlstore.tlstore.model.*;
import com.tl.tlstore.tlstore.service.CategoryService;
import com.tl.tlstore.tlstore.service.OrderService;
import com.tl.tlstore.tlstore.service.ProductService;
import com.tl.tlstore.tlstore.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private OrderService  orderService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private S3Service s3Service;

    @GetMapping("/index")
    public String admin() {
        return "redirect:/admin/quanlydonhang";
    }

    @GetMapping("/quanlydonhang")
    public String quanLyDonHang(Model model) {
        List<Order> pendingOrders = orderService.findAllByStatus("pending");
        List<Order> shippingOrders = orderService.findAllByStatus("shipping");
        List<Order> deliveredOrders = orderService.findAllByStatus("delivered");
        List<Order> cancelledOrders = orderService.findAllByStatus("cancelled");

        model.addAttribute("pendingOrders", pendingOrders);
        model.addAttribute("shippingOrders", shippingOrders);
        model.addAttribute("deliveredOrders", deliveredOrders);
        model.addAttribute("cancelledOrders", cancelledOrders);

        return "admin/quanlydonhang";
    }

    @GetMapping("/quanlysanpham")
    public String quanLySanPham(@RequestParam(name = "function", required = false, defaultValue = "view-product") String function,
                                @RequestParam(name = "id", required = false) Long id,
                                Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("function", function);

        if (function.equals("update-product") && id != null) {
            Product product = productService.findById(id);
            model.addAttribute("product", product);
            return "admin/quanlysanpham";
        }

        return "admin/quanlysanpham";
    }
    // Function view order detail
    @GetMapping("/order")
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
        return "admin/orderDetail";
    }

    // Function update order status
    @GetMapping("/order/status")
    public String updateOrderStatus(@RequestParam Long id, @RequestParam String status, Model model) {
        Order order = orderService.updateStatus(id, status);
        return "redirect:/admin/quanlydonhang";
    }

    // Function add product
    @PostMapping("/product")
    public String addProduct(@RequestParam String name,
                             @RequestParam Long price,
                             @RequestParam Long quantity,
                             @RequestParam String description,
                             @RequestParam Long category,
                             @RequestParam("image") MultipartFile image)
    {
        // Create a new Product object
        Product product = new Product();

        product.setName(name);
        product.setPrice(price.doubleValue());
        product.setQuantity(quantity.intValue());
        product.setDescription(description);

        Optional<Category> categoryOptional = categoryService.findById(category);
        if (categoryOptional.isPresent()) {
            product.setCategory(categoryOptional.get());
        }

        String imageUrl = s3Service.uploadFile(image);
        product.setImage(imageUrl);

        productService.save(product);

        return "redirect:/admin/quanlysanpham";
    }

    @PutMapping("/product")
    public String updateProduct(@RequestParam String id,
                                @RequestParam String name,
                                @RequestParam String price,
                                @RequestParam String quantity,
                                @RequestParam String description,
                                @RequestParam String category,
                                @RequestParam(value = "image", required = false) MultipartFile image,
                                RedirectAttributes model) {
        Product product = productService.findById(Long.parseLong(id));
        product.setName(name);
        product.setPrice(Double.parseDouble(price));
        product.setQuantity(Integer.parseInt(quantity));
        product.setDescription(description);

        Optional<Category> categoryOptional = categoryService.findById(Long.parseLong(category));
        if (categoryOptional.isPresent()) {
            product.setCategory(categoryOptional.get());
        }
        if (image != null && !image.isEmpty()) {
            String imageUrl = s3Service.uploadFile(image);
            product.setImage(imageUrl);
        }

        productService.save(product);
        model.addFlashAttribute("message", "Lưu sản phẩm thành công");
        return "redirect:/admin/quanlysanpham?function=update-product&id=" + id;
    }

    @GetMapping("/searchProducts")
    public String searchProducts(@RequestParam(name = "category", required = true) Long categoryId,
                                 @RequestParam(name = "name", required = true) String name,
                                 RedirectAttributes model) {
        List<Product> products = productService.findAllByCategoryIdAndNameContaining(categoryId, name);
        model.addFlashAttribute("products", products);
        return "redirect:/admin/quanlysanpham?function=view-product";
    }
}
