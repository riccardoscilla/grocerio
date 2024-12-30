package com.grocerio.auth;

import com.grocerio.auth.model.SignInRequest;
import com.grocerio.auth.model.SignUpRequest;
import com.grocerio.auth.model.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signIn")
    public ResponseEntity<TokenResponse> singIn(
            @RequestBody SignInRequest request
    ) {
        return ResponseEntity.ok(authService.signIn(request));
    }

    @PostMapping("/signUp")
    public ResponseEntity<TokenResponse> signup(
            @RequestBody SignUpRequest request
    ) {
        return ResponseEntity.ok(authService.signUp(request));
    }

}
