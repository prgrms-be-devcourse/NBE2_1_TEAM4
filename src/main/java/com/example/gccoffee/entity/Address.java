package com.example.gccoffee.entity;

import lombok.Getter;

import jakarta.persistence.Embeddable;

@Embeddable
@Getter
public class Address {

    private String city;
    private String street;
    private String postcode;

    protected Address() {
    }

    public Address(String city, String street, String postcode) {
        this.city = city;
        this.street = street;
        this.postcode = postcode;
    }
}