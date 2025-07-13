package com.ashokvocab.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class PingController {

    @GetMapping("/ping")
    public String ping() {
        return "server is up and running at " + LocalDateTime.now();

    }
}