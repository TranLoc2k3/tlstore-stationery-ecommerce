package com.tl.tlstore.tlstore.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "roles")
@Data
public class Role {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "role")
    private String role;

    @Column(name = "username")
    private String username;

    public Role(User user, String role, String username) {
        this.user = user;
        this.role = role;
        this.username = username;
    }

    public Role() {

    }
}