package com.tl.tlstore.tlstore.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@DiscriminatorValue("customer")
@Data
public class Customer extends User{
}
