package com.example.coffee.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "gc-Member")
public class Member {
    @Id
    private String mid;

    private String mpw;
}
