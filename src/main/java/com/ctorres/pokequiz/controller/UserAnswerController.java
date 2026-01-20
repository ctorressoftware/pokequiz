package com.ctorres.pokequiz.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/user-answer")
public class UserAnswerController {

    @GetMapping(path = "/user-answering")
    public String answering() {
        return "HOLA";
    }
}
