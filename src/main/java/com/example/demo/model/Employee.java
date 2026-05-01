package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    private String skills;
    private String experience;

    // 🔥 ADD THESE
    private String password;
    private String role;
    private Double salary;
    private String manager;
    private String department;
}
