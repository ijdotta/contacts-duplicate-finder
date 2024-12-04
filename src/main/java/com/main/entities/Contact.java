package com.main.entities;

public record Contact(
        int id,
        String name,
        String lastname,
        String email,
        Integer zipcode,
        String address
) { }
