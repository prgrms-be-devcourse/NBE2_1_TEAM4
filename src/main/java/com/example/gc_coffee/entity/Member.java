package com.example.gc_coffee.entity;

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
    private Email email; // 이메일 주소를 Email 클래스로 대체

    @Embedded
    private Address address; // 주소 정보

    // 주문과의 관계를 유지
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

}