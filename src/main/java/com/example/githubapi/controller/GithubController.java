package com.example.githubapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/repositories")
public class GithubController {
    @GetMapping
    ResponseEntity<String>listUserRepositories(){
        return ResponseEntity.ok("repositories");
    }
}
