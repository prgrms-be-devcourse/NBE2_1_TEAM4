package com.example.gccoffee.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Embedded
    private Email email;

    @Embedded
    private Address address; // 주소 정보


    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

}