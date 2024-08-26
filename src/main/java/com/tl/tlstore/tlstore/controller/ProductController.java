package com.tl.tlstore.tlstore.controller;

import com.tl.tlstore.tlstore.model.Category;
import com.tl.tlstore.tlstore.model.Product;
import com.tl.tlstore.tlstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public String getProductDetail(@RequestParam("id") Long productId, Model model) {
        Product product = productService.findById(productId);
        Category category = product.getCategory();
        List<Product> products = productService.findTopProductsByCategoryNameLike(category.getName(), 12);
        if (product == null) {
            return "errors/404";
        }
        model.addAttribute("product", product);
        model.addAttribute("category", category);
        model.addAttribute("products", products);
        return "ecommerce/productDetail.html";
    }

    @GetMapping("/search")
    public String searchProductByName(@RequestParam String name, Model model) {
        return "redirect:/";
    }

    @GetMapping("/sach")
    public String sach(Model model) {
        List<Product> products = productService.findTopProductsByCategoryNameLike("Sách", 24); // Tam thoi 24 san pham
        model.addAttribute("products", products);
        model.addAttribute("productTitle", "Sách");
        return "ecommerce/categories.html";
    }
    @GetMapping("/tapvo")
    public String tapvo(Model model) {
        List<Product> products = productService.findTopProductsByCategoryNameLike("Tập vở", 24); // Tam thoi 24 san pham
        model.addAttribute("products", products);
        model.addAttribute("productTitle", "Tập vở");
        return "ecommerce/categories.html";
    }
    @GetMapping("/dungcuhoctap")
    public String dungcuhoctap(Model model) {
        List<Product> products = productService.findTopProductsByCategoryNameLike("Dụng cụ học tập", 24); // Tam thoi 24 san pham
        model.addAttribute("products", products);
        model.addAttribute("productTitle", "Dụng cụ học tập");
        return "ecommerce/categories.html";
    }
    @GetMapping("/quatang")
    public String quatang(Model model) {
        List<Product> products = productService.findTopProductsByCategoryNameLike("Quà tặng", 24); // Tam thoi 24 san pham
        model.addAttribute("products", products);
        model.addAttribute("productTitle", "Quà tặng");
        return "ecommerce/categories.html";
    }
}
