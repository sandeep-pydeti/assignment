package com.assignment.apica.entity;

import com.assignment.apica.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;


@Entity
@Data
public class Users extends BaseEntity{
    private String fullName;
    private long phoneNumber;
    private String username;
    private String email;
    private String password;
    private String role;
}
