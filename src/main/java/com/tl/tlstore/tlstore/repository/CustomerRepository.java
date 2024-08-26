package com.tl.tlstore.tlstore.repository;

import com.tl.tlstore.tlstore.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}