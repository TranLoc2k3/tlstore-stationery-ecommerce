package com.tl.tlstore.tlstore.service.impl;

import com.tl.tlstore.tlstore.model.Address;
import com.tl.tlstore.tlstore.model.Role;
import com.tl.tlstore.tlstore.model.User;
import com.tl.tlstore.tlstore.repository.AddressRepository;
import com.tl.tlstore.tlstore.repository.RoleRepository;
import com.tl.tlstore.tlstore.repository.UserRepository;
import com.tl.tlstore.tlstore.service.AddressService;
import com.tl.tlstore.tlstore.service.EmailService;
import com.tl.tlstore.tlstore.service.UserService;
import com.tl.tlstore.tlstore.util.OtpStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailService emailService;
    @Autowired
    private AddressRepository addressRepository;

    @Override
    public String registerUser(String username, String password) {
        // Check exist username(email)
        boolean isExist = userRepository.existsByEmail(username);
        if (isExist) {
           return "Email already exists";
        }
        // Save new account without verify email
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(username);
        user.setUserType("customer");
        user.setActive(false); // After verify OTP, set active = true
        Role role = new Role(user, "ROLE_CUSTOMER", username);
        roleRepository.save(role);

        // Send OTP to email
        emailService.sendOtpMessage(username, "TLStore - Verify your email address");

        return "Register success";
    }

    @Override
    public String verifyOtp(String email, String otp) {
        String otpStore = OtpStore.getOtp(email);
        if (otp.equals(otpStore)) {
            User user = userRepository.findByUsername(email);
            user.setActive(true);
            userRepository.save(user);

            OtpStore.removeOtp(email);
            return "Verify success";
        }
        return "Verify failed";
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByUsername(email);
    }

    @Override
    public Boolean updateProfileUser(User user) {
        User userUpdate = userRepository.findByUsername(user.getUsername());
        if (userUpdate != null) {
            userUpdate.setFirstName(user.getFirstName());
            userUpdate.setLastName(user.getLastName());
            userUpdate.setPhone(user.getPhone());
            userRepository.save(userUpdate);
            return true;
        }
        return false;
    }

    @Override
    public Boolean updateAddressUser(String username, Address address) {
        User user = userRepository.findByUsername(username);
        Address savedAddress = addressRepository.save(address);
        if (user != null) {
            if (user.getAddress() != null) {
                Address address1 = user.getAddress();
                addressRepository.delete(address1);
            }
            user.setAddress(address);
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
