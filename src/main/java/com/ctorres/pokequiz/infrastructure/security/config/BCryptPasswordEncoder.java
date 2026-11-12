package com.ctorres.pokequiz.infrastructure.security.config;

import com.ctorres.pokequiz.application.port.out.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordEncoder implements PasswordEncoder {

    private final org.springframework.security.crypto.password.PasswordEncoder encoder;

    public BCryptPasswordEncoder(org.springframework.security.crypto.password.PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return encoder.encode(rawPassword);
    }
}
