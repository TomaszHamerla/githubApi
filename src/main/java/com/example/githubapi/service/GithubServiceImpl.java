package com.example.githubapi.service;

import com.example.githubapi.configuration.ConfigProperties;
import com.example.githubapi.model.Branch;
import com.example.githubapi.model.BranchInfo;
import com.example.githubapi.model.GithubResponse;
import com.example.githubapi.model.RepositoryInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GithubServiceImpl implements GithubService {
    private final ConfigProperties prop;
    private final RestTemplate restTemplate;

    @Override
    public List<RepositoryInfo> getUserRepositories(String username) {
        String apiUrl = String.format("%s/users/%s/repos", prop.getGithubBaseUrl(), username);
        // Not handling the NotFound exception here because it's handled globally through RestControllerAdvice
        GithubResponse[] response = restTemplate.getForObject(apiUrl, GithubResponse[].class);
        return Arrays.stream(response)
                .filter(r -> !r.fork())
                .map(r -> new RepositoryInfo(r.name(), r.owner(), getBranches(username, r.name())))
                .toList();
    }

    private List<BranchInfo> getBranches(String username, String repoName) {
        String branchUrl = String.format("%s/repos/%s/%s/branches", prop.getGithubBaseUrl(), username, repoName);
        Branch[] response = restTemplate.getForObject(branchUrl, Branch[].class);
        return Arrays.stream(response)
                .map(b -> new BranchInfo(b.name(), b.commit().sha()))
                .toList();
    }
}
