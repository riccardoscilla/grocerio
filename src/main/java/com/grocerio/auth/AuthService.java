package com.grocerio.auth;

import com.grocerio.auth.model.EmailPasswordRequest;
import com.grocerio.auth.model.SignInRequest;
import com.grocerio.auth.model.SignUpRequest;
import com.grocerio.auth.model.TokenResponse;
import com.grocerio.entities.shelf.ShelfService;
import com.grocerio.entities.shelf.model.Shelf;
import com.grocerio.entities.shelf.model.ShelfNew;
import com.grocerio.entities.user.UserService;
import com.grocerio.entities.user.model.UserNew;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
@DependsOn("supabaseConfig")
public class AuthService {
    private final UserService userService;
    private final ShelfService shelfService;
    private final RestTemplate restTemplate;

    @Value("${supabase.api_url}")
    String apiUrl;

    @Value("${supabase.api_key}")
    String apiKey;

    Logger logger = LoggerFactory.getLogger(AuthService.class);

    public AuthService(UserService userService, ShelfService shelfService, RestTemplate restTemplate) {
        this.userService = userService;
        this.shelfService = shelfService;
        this.restTemplate = restTemplate;
    }

    public TokenResponse signIn(SignInRequest request) {
        String url = apiUrl + "/auth/v1/token?grant_type=password";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apiKey", apiKey);

        EmailPasswordRequest emailPasswordRequest =
                new EmailPasswordRequest(request.email, request.password);

        HttpEntity<EmailPasswordRequest> entity = new HttpEntity<>(emailPasswordRequest, headers);
        ResponseEntity<TokenResponse> response = restTemplate.postForEntity(url, entity, TokenResponse.class);

        return response.getBody();
    }

    public TokenResponse signUp(SignUpRequest request) {
        String url = apiUrl + "/auth/v1/signup";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apiKey", apiKey);

        EmailPasswordRequest emailPasswordRequest =
                new EmailPasswordRequest(request.email, request.password);

        HttpEntity<EmailPasswordRequest> entity = new HttpEntity<>(emailPasswordRequest, headers);
        ResponseEntity<TokenResponse> response = restTemplate.postForEntity(url, entity, TokenResponse.class);
        TokenResponse tokenResponse = response.getBody();

        // Create shelf
        Shelf shelf;
        if (request.shareId != null) {
            shelf = shelfService.get(request.shareId);
        }
        else {
            ShelfNew shelfNew = new ShelfNew();
            shelfNew.name = request.shelfName;
            shelf = shelfService.save(shelfNew);
        }

        // Create user
        UserNew userNew = new UserNew();
        userNew.uuid = UUID.fromString(tokenResponse.user.id);
        userNew.shelfId = shelf.id;
        userService.save(userNew);

        return tokenResponse;
    }

}
