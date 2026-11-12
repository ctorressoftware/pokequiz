package com.ctorres.pokequiz.application.port.out;

public interface PasswordEncoder {
    String encode(CharSequence rawPassword);
}
