package com.ctorres.pokequiz.controller;

import com.ctorres.pokequiz.dto.api.ApiResponse;
import com.ctorres.pokequiz.dto.auth.*;
import com.ctorres.pokequiz.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<ApiResponse<TokenResponse>> register(
            @RequestBody RegisterRequest req,
            @RequestHeader(value = "User-Agent", required = false) String ua,
            @RequestHeader(value = "X-Forwarded-For", required = false) String xff,
            HttpServletRequest http) {
        var ip = xff != null ? xff : http.getRemoteAddr();
        var response = auth.register(req.getUsername(), req.getPassword(), ip, ua);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponse>> login(
            @RequestBody LoginRequest req,
            @RequestHeader(value = "User-Agent", required = false) String ua,
            @RequestHeader(value = "X-Forwarded-For", required = false) String xff,
            HttpServletRequest http) {
        var ip = xff != null ? xff : http.getRemoteAddr();
        var response = auth.login(req.getUsername(), req.getPassword(), ip, ua);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<TokenResponse>> refresh(
            @RequestBody RefreshRequest req,
            @RequestHeader(value = "User-Agent", required = false) String ua,
            @RequestHeader(value = "X-Forwarded-For", required = false) String xff,
            HttpServletRequest http) {
        var ip = xff != null ? xff : http.getRemoteAddr();
        var response = auth.refresh(req.getRefreshToken(), ip, ua);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@RequestBody RefreshRequest req) {
        auth.logout(req.getRefreshToken());
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}