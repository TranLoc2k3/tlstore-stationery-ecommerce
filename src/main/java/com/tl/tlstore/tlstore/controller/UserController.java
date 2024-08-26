package com.tl.tlstore.tlstore.controller;

import com.tl.tlstore.tlstore.model.Address;
import com.tl.tlstore.tlstore.model.Product;
import com.tl.tlstore.tlstore.model.User;
import com.tl.tlstore.tlstore.repository.ProductRepository;
import com.tl.tlstore.tlstore.service.ProductService;
import com.tl.tlstore.tlstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;


    @GetMapping("/test")
    public String test(Model model) {
        List<Product>  products = productService.findTopProductsByCategoryNameLike("Sách", 24);
        model.addAttribute("products", products);

        return "ecommerce/categories.html";
    }

    @GetMapping("/")
    public String homePage(Model model) {
        List<Product>  products = productService.findTopProductsByCategoryNameLike("Sách", 12);
        List<Product> products4 = productService.findTopProductsByCategoryNameLike("Tập vở", 12);
        List<Product> products2 = productService.findTopProductsByCategoryNameLike("Dụng cụ học tập", 12);
        List<Product> products3 = productService.findTopProductsByCategoryNameLike("Quà tặng", 12);
        model.addAttribute("danhSachSach", products);
        model.addAttribute("danhSachDungCuHocTap", products2);
        model.addAttribute("danhSachQuaTang", products3);
        model.addAttribute("danhSachTapVo", products4);
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !authentication.getName().equals("anonymousUser")) {
            return "redirect:/";
        }
        return "user/login";
    }

    @GetMapping("/register")
    public String registerGet() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !authentication.getName().equals("anonymousUser")) {
            return "redirect:/";
        }
        return "user/register";
    }

    // Form data: username, password (username is email)
    @PostMapping("/register")
    public String registerPost(@RequestParam("username") String username,
                               @RequestParam("password") String password,
                               RedirectAttributes redirectAttributes) {

        String result = userService.registerUser(username, password);
        if (result.equals("Email already exists")) {
            return "redirect:/register?error";
        }

        // Verify email OTP
        redirectAttributes.addFlashAttribute("email", username);
        return "redirect:/verify-otp";
    }

    @GetMapping("/verify-otp")
    public String verifyOtpGet() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !authentication.getName().equals("anonymousUser")) {
            return "redirect:/";
        }
        return "user/verify-otp";
    }

    @PostMapping("/verify-otp")
    @ResponseBody
    public ResponseEntity<?> verifyOtpPost(@RequestParam("email") String email, @RequestParam("otp") String otp) {
        String result = userService.verifyOtp(email, otp);
        if (result.equals("Verify success")) {
            return ResponseEntity.ok(Collections.singletonMap("status", "success"));
        } else {
            return ResponseEntity.ok(Collections.singletonMap("status", "error"));
        }
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        model.addAttribute("email", username);
        User user = userService.getUserByEmail(username);
        if (user != null) {
            if (user.getFirstName() != null)
                model.addAttribute("firstName", user.getFirstName());
            if (user.getLastName() != null)
                model.addAttribute("lastName", user.getLastName());
            if (user.getPhone() != null)
                model.addAttribute("phone", user.getPhone());
            Address address = user.getAddress();
            if (address != null) {
                if (address.getProvince() != null)
                    model.addAttribute("province", address.getProvince());
                if (address.getDistrict() != null)
                    model.addAttribute("district", address.getDistrict());
                if (address.getWard() != null)
                    model.addAttribute("ward", address.getWard());
                if (address.getAddress_detail() != null)
                    model.addAttribute("address_detail", address.getAddress_detail());
            }
        }
        return "user/profile";
    }

    @PostMapping("/updateProfile")
    public String updateProfile(    @RequestParam String firstName,
                                                    @RequestParam String lastName,
                                                    @RequestParam String phone,
                                                    @RequestParam String email) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhone(phone);
        user.setUsername(email);
        user.setEmail(email);
        Boolean res = userService.updateProfileUser(user);
        if (res) {
            return "redirect:/profile?successprofile";
        }
        return "redirect:/profile?errorprofile";
    }

    @PostMapping("/updateAddress")
    public String updateAddress(    @RequestParam String province,
                                                    @RequestParam String district,
                                                    @RequestParam String ward,
                                                    @RequestParam String address_detail) {
        Address address = new Address();
        address.setProvince(province);
        address.setDistrict(district);
        address.setWard(ward);
        address.setAddress_detail(address_detail);

        // Get username (email) from authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Boolean res = userService.updateAddressUser(username, address);
        if (res) {
            return "redirect:/profile?successaddress";
        }
        return "redirect:/profile?erroraddress";
    }
}
