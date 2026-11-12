package com.ctorres.pokequiz.infrastructure.security;

import com.ctorres.pokequiz.application.port.out.Authenticator;
import com.ctorres.pokequiz.application.port.out.TokenIssuer;
import com.ctorres.pokequiz.application.usecase.auth.AccessToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class AuthenticationService implements Authenticator {

    private final AuthenticationManager authenticationManager;
    private final TokenIssuer tokenIssuer;

    public AuthenticationService(
            AuthenticationManager authenticationManager,
            TokenIssuer tokenIssuer
    ) {
        this.authenticationManager = authenticationManager;
        this.tokenIssuer = tokenIssuer;
    }

    @Override
    public AccessToken authenticate(String username, String rawPassword) {

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(username, rawPassword);

        org.springframework.security.core.Authentication auth =
                authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(auth);

        AuthUser user = (AuthUser) auth.getPrincipal();

        return new AccessToken(tokenIssuer.issueAccessToken(
                user.getId(),
                user.getUsername(),
                user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList())
        ));
    }
}
