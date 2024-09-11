package com.example.gccoffee.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import org.springframework.util.Assert;

import java.util.Objects;
import java.util.regex.Pattern;

@Getter
@Embeddable
public class Email {

    private final String emailAddress;

    public Email(String emailAddress) {
        Assert.notNull(emailAddress,"address should not be null");
        Assert.isTrue(emailAddress.length()>=4 && emailAddress.length()<=50,"address length must be between 4 and 50 characters.");
        Assert.isTrue(checkAddress(emailAddress),"Invalid email address");
        this.emailAddress = emailAddress;
    }

    public Email() {
        this.emailAddress = null; // Jpa 사용을 위해서
    }

    private static boolean checkAddress(String address){
        return Pattern.matches("\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b",address);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(emailAddress, email.emailAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(emailAddress);
    }

    @Override
    public String toString() {
        return "Email{" +
                "address='" + emailAddress + '\'' +
                '}';
    }

}
