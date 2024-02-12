package com.example.githubapi.service;

import com.example.githubapi.model.RepositoryInfo;

import java.util.List;

public interface GithubService {
    List<RepositoryInfo>getUserRepositories(String username);
}
