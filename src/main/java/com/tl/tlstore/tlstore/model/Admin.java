package com.tl.tlstore.tlstore.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@DiscriminatorValue("admin")
@Data
public class Admin extends User{

}
