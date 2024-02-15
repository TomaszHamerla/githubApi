package com.example.githubapi.model;

import java.util.List;

public record RepositoryInfo(String repositoryName, Owner owner, List<BranchInfo> branches) {
}
