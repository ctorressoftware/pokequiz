package com.ctorres.pokequiz.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ctorres.pokequiz.util.Constants;;

@RestController
@RequestMapping(Constants.POKEMON)
public class PokequizController {
    
    @GetMapping(Constants.OBTENER_POKEMON)
    public String getPokemon() {
        return "Pikachu!";
    }
    

}
