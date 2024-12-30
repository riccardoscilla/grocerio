package com.grocerio.auth.model;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public class SignUpRequest {
    @NotBlank
    public String email;

    @NotBlank
    public String password;

    @NotBlank
    public String repeatedPassword;

    public String shelfName;

    public String shareId;

    @AssertTrue
    private boolean passwordSame() {
        return password.equals(repeatedPassword);
    }

    @AssertTrue
    private boolean shelfNameOrShareId() {
        return shelfName != null || shareId != null;
    }
}
