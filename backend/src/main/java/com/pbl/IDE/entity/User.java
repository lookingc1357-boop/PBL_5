package com.pbl.IDE.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    private String username;
    private String email;
    private String fullName;
    // set password not null
    @Column(nullable = false)
    private String password;
}