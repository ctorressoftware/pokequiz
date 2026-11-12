package com.ctorres.pokequiz.application.port.out;

import java.util.Map;

public interface TokenDecoder {
    Map<String, String> parse(String stringToken);
    String getSubject(String jwt);
}
