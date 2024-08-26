package com.tl.tlstore.tlstore.service.impl;

import com.tl.tlstore.tlstore.model.Address;
import com.tl.tlstore.tlstore.repository.AddressRepository;
import com.tl.tlstore.tlstore.service.AddressService;
import com.tl.tlstore.tlstore.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Address save(Address address) {
        Address res = addressRepository.save(address);
        return res;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }
}
