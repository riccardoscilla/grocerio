package com.grocerio.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.grocerio.auth.model.SignInRequest;
import com.grocerio.auth.model.SignInResponse;
import com.grocerio.auth.model.SignupRequest;
import com.grocerio.auth.model.SignupResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(
            @RequestBody SignupRequest request
    ) {
        return ResponseEntity.ok(authService.signup(request));
    }

    @PostMapping("/signIn")
    public ResponseEntity<SignInResponse> singIn(
            @RequestBody SignInRequest request
    ) {
        return ResponseEntity.ok(authService.signIn(request));
    }

}
