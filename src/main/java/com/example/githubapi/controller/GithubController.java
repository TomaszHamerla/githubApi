package com.example.githubapi.controller;

import com.example.githubapi.model.RepositoryInfo;
import com.example.githubapi.service.GithubService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/repositories")
@RequiredArgsConstructor
public class GithubController {
    private final GithubService githubService;

    @GetMapping("/{username}")
    List<RepositoryInfo> getUserRepositories(@PathVariable String username) {
        return githubService.getUserRepositories(username);
    }
}
