package com.grocerio.entities.user.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class UserNew {
    @NotBlank
    public UUID uuid;

    @NotNull
    public Long shelfId;
}
