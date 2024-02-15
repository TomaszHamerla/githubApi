package com.example.githubapi.model;

public record GithubResponse(String name, Owner owner, boolean fork) {
    public boolean isNotFork(){
        return !fork;
    }
}
