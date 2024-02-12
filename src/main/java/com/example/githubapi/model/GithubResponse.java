package com.example.githubapi.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GithubResponse {
    private String name;
    private Owner owner;
    private boolean fork;
}
