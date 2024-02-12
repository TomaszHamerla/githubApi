package com.example.githubapi.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder
@Getter
@Setter
public class BranchInfo {
    private String name;
    private String lastCommitSha;
}
