package com.grocerio.auth.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EmailPasswordRequest {
    public String email;
    public String password;
}
