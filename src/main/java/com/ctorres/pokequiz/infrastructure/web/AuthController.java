package com.ctorres.pokequiz.infrastructure.web;

import com.ctorres.pokequiz.application.port.in.auth.login.LoginCommand;
import com.ctorres.pokequiz.application.port.in.auth.logout.LogoutCommand;
import com.ctorres.pokequiz.application.port.in.auth.refresh.RefreshCommand;
import com.ctorres.pokequiz.application.port.in.auth.register.RegisterCommand;
import com.ctorres.pokequiz.infrastructure.web.api.ApiResponse;
import com.ctorres.pokequiz.application.usecase.auth.AuthService;
import com.ctorres.pokequiz.infrastructure.web.request.auth.LoginRequest;
import com.ctorres.pokequiz.infrastructure.web.request.auth.RefreshRequest;
import com.ctorres.pokequiz.infrastructure.web.request.auth.RegisterRequest;
import com.ctorres.pokequiz.infrastructure.web.response.auth.TokenResponse;
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
        var response = auth.register(new RegisterCommand(
                req.getUsername(),
                req.getPassword(),
                ip,
                ua
        ));
        return ResponseEntity.ok(ApiResponse.success(response.tokenResponse()));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponse>> login(
            @RequestBody LoginRequest req,
            @RequestHeader(value = "User-Agent", required = false) String ua,
            @RequestHeader(value = "X-Forwarded-For", required = false) String xff,
            HttpServletRequest http) {
        var ip = xff != null ? xff : http.getRemoteAddr();
        var response = auth.login(new LoginCommand(
                req.getUsername(),
                req.getPassword(),
                ip,
                ua
        ));

        return ResponseEntity.ok(ApiResponse.success(response.tokenResponse()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<TokenResponse>> refresh(
            @RequestBody RefreshRequest req,
            @RequestHeader(value = "User-Agent", required = false) String ua,
            @RequestHeader(value = "X-Forwarded-For", required = false) String xff,
            HttpServletRequest http) {
        var ip = xff != null ? xff : http.getRemoteAddr();
        var response = auth.refresh(new RefreshCommand(
                req.getRefreshToken(),
                ip,
                ua
        ));
        return ResponseEntity.ok(ApiResponse.success(response.tokenResponse()));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@RequestBody RefreshRequest req) {
        auth.logout(new LogoutCommand(req.getRefreshToken()));
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}