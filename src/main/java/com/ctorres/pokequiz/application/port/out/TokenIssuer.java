package com.ctorres.pokequiz.application.port.out;

import java.util.Collection;
import java.util.UUID;

public interface TokenIssuer {
    String issueAccessToken(UUID id, String username, Collection<String> roles);
}
