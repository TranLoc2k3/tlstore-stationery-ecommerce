package com.tl.tlstore.tlstore.service;

import com.tl.tlstore.tlstore.model.Address;
import com.tl.tlstore.tlstore.model.User;

public interface UserService {
    String registerUser(String username, String password);
    String verifyOtp(String email, String otp);
    User getUserByEmail(String email);
    Boolean updateProfileUser(User user);
    Boolean updateAddressUser(String username, Address address);
}
