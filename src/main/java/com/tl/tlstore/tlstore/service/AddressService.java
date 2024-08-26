package com.tl.tlstore.tlstore.service;

import com.tl.tlstore.tlstore.model.Address;

public interface AddressService {
    Address save(Address address);
    Boolean delete(Long id);
}
