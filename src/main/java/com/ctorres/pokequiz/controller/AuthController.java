package com.ctorres.pokequiz.controller;

import com.ctorres.pokequiz.dto.auth.*;
import com.ctorres.pokequiz.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    private final AuthService auth;

    public AuthController(AuthService auth) {
        this.auth = auth;
    }

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@RequestBody RegisterRequest req,
            @RequestHeader(value = "User-Agent", required = false) String ua,
            @RequestHeader(value = "X-Forwarded-For", required = false) String xff,
            jakarta.servlet.http.HttpServletRequest http) {
        var ip = xff != null ? xff : http.getRemoteAddr();
        var pair = auth.register(req.getUsername(), req.getPassword(), ip, ua);
        return ResponseEntity.ok(new TokenResponse(pair[0], pair[1]));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest req,
            @RequestHeader(value = "User-Agent", required = false) String ua,
            @RequestHeader(value = "X-Forwarded-For", required = false) String xff,
            jakarta.servlet.http.HttpServletRequest http) {
        var ip = xff != null ? xff : http.getRemoteAddr();
        var pair = auth.login(req.getUsername(), req.getPassword(), ip, ua);
        return ResponseEntity.ok(new TokenResponse(pair[0], pair[1]));
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(@RequestBody RefreshRequest req,
            @RequestHeader(value = "User-Agent", required = false) String ua,
            @RequestHeader(value = "X-Forwarded-For", required = false) String xff,
            jakarta.servlet.http.HttpServletRequest http) {
        var ip = xff != null ? xff : http.getRemoteAddr();
        var pair = auth.refresh(req.getRefreshToken(), ip, ua);
        return ResponseEntity.ok(new TokenResponse(pair[0], pair[1]));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody RefreshRequest req) {
        auth.logout(req.getRefreshToken());
        return ResponseEntity.noContent().build();
    }
}