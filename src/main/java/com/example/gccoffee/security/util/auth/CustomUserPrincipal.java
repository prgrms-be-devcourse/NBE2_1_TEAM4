package com.example.gccoffee.security.util.auth;

import lombok.RequiredArgsConstructor;

import java.security.Principal;

@RequiredArgsConstructor
public class CustomUserPrincipal implements Principal {
    private final String name;

    @Override
    public String getName() {
        return "name";
    }
}
