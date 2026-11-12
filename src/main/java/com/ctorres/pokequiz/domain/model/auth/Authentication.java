package com.ctorres.pokequiz.domain.model.auth;

public record Authentication(User principal) {
    public User getPrincipal() {
        return principal;
    }
}
