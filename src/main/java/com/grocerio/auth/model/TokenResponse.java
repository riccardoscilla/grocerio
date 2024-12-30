package com.grocerio.auth.model;

import com.grocerio.entities.user.model.User;

public class TokenResponse {
    public String access_token;
    public String token_type;
    public int expires_in;
    public long expires_at;
    public String refresh_token;
    public UserResponse user;

    public static class UserResponse {
        public String id;
    }
}
