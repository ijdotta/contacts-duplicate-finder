package com.main.entities;

import org.jetbrains.annotations.Nullable;

public record Contact(
        int id,
        @Nullable String name,
        @Nullable String lastname,
        @Nullable String email,
        @Nullable Integer zipcode,
        @Nullable String address
) { }
