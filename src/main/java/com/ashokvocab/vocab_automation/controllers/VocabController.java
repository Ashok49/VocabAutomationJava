package com.ashokvocab.vocab_automation.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vocab")
public class VocabController {

    @GetMapping("/test")
    public String test(){
        return "Hello from Spring Boot !!!";
    }
}
