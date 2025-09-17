package com.ctorres.pokequiz.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class CommonUtils {

    public CommonUtils() {}

    public static List<String> getAlphabet() {
        List<String> alphabet = new ArrayList<>();
        for (char c = 'a'; c <= 'z'; c++) {
            alphabet.add(String.valueOf(c));
        }        
        return alphabet;
    }
}
