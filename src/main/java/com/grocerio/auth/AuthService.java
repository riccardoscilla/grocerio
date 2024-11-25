package com.grocerio.auth;

import com.grocerio.auth.model.SignInRequest;
import com.grocerio.auth.model.SignInResponse;
import com.grocerio.auth.model.SignupRequest;
import com.grocerio.auth.model.SignupResponse;
import com.grocerio.entities.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@DependsOn("supabaseConfig")
public class AuthService {
    private final RestTemplate restTemplate;

    @Value("${supabase.api_url}")
    String apiUrl;

    @Value("${supabase.api_key}")
    String apiKey;

    Logger logger = LoggerFactory.getLogger(AuthService.class);

    public AuthService(UserService userService, RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public SignInResponse signIn(SignInRequest request) {
        String url = apiUrl + "/auth/v1/token?grant_type=password";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apiKey", apiKey);

        HttpEntity<SignInRequest> entity = new HttpEntity<>(request, headers);
        ResponseEntity<SignInResponse> response = restTemplate.postForEntity(url, entity, SignInResponse.class);

        return response.getBody();
    }

    public SignupResponse signup(SignupRequest request) {
        String url = apiUrl + "/auth/v1/signup";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apiKey", apiKey);

        HttpEntity<SignupRequest> entity = new HttpEntity<>(request, headers);
        ResponseEntity<SignupResponse> response = restTemplate.postForEntity(url, entity, SignupResponse.class);

        return response.getBody();
    }

}
