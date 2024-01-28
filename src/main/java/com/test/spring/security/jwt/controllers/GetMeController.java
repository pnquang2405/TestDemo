package com.test.spring.security.jwt.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class GetMeController {
    @GetMapping("/GetMe")
    public String allAccess() {
        return "Hello, I'am Pham Ngoc Quang.";
    }
}
