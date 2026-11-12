package com.ctorres.pokequiz.application.port.out;

import com.ctorres.pokequiz.application.usecase.auth.AccessToken;

public interface Authenticator {
    AccessToken authenticate(String username, String rawPassword);
}