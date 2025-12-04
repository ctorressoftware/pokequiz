package com.ctorres.pokequiz.controller;

import com.ctorres.pokequiz.dto.api.ApiResponse;
import com.ctorres.pokequiz.dto.auth.*;
import com.ctorres.pokequiz.exception.DuplicatedUsernameException;
import com.ctorres.pokequiz.exception.InactiveUserException;
import com.ctorres.pokequiz.exception.InvalidRefreshTokenException;
import com.ctorres.pokequiz.exception.WeakPasswordException;
import com.ctorres.pokequiz.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
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
        TokenResponse response = null;
        var ip = xff != null ? xff : http.getRemoteAddr();

        try {
            response = auth.register(req.getUsername(), req.getPassword(), ip, ua);

        } catch (DuplicatedUsernameException | WeakPasswordException e) {
            return ResponseEntity
                    .badRequest()
                    .body(ApiResponse.error(400, e.getMessage()));
        }
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponse>> login(
            @RequestBody LoginRequest req,
            @RequestHeader(value = "User-Agent", required = false) String ua,
            @RequestHeader(value = "X-Forwarded-For", required = false) String xff,
            HttpServletRequest http) {
        TokenResponse response = null;
        var ip = xff != null ? xff : http.getRemoteAddr();

        try {
            response = auth.login(req.getUsername(), req.getPassword(), ip, ua);
        } catch (BadCredentialsException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error(HttpStatus.UNAUTHORIZED.value(), "Bad credentials"));
        }
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<TokenResponse>> refresh(
            @RequestBody RefreshRequest req,
            @RequestHeader(value = "User-Agent", required = false) String ua,
            @RequestHeader(value = "X-Forwarded-For", required = false) String xff,
            HttpServletRequest http) {
        TokenResponse response = null;
        var ip = xff != null ? xff : http.getRemoteAddr();

        try {
            response = auth.refresh(req.getRefreshToken(), ip, ua);
        } catch (InvalidRefreshTokenException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error(HttpStatus.UNAUTHORIZED.value(), e.getMessage()));
        } catch (InactiveUserException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error(HttpStatus.UNAUTHORIZED.value(), "Bad credentials"));
        }
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@RequestBody RefreshRequest req) {
        auth.logout(req.getRefreshToken());
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}