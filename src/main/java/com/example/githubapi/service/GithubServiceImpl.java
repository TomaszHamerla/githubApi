package com.example.githubapi.service;

import com.example.githubapi.exceptionHandler.UserNotFoundException;
import com.example.githubapi.model.Branch;
import com.example.githubapi.model.BranchInfo;
import com.example.githubapi.model.GithubResponse;
import com.example.githubapi.model.RepositoryInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GithubServiceImpl implements GithubService {
    private final RestClient restClient;

    @Override
    public List<RepositoryInfo> getUserRepositories(String username) {
        var uri = String.format("/users/%s/repos", username);

        GithubResponse[] githubResponses = restClient.get()
                .uri(uri)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new UserNotFoundException(response.getStatusText());
                })
                .body(GithubResponse[].class);

        return Arrays.stream(githubResponses)
                .filter(GithubResponse::isNotFork)
                .map(r -> new RepositoryInfo(r.name(), r.owner(), getBranches(username, r.name())))
                .toList();
    }

    private List<BranchInfo> getBranches(String username, String repoName) {
        var uri = String.format("/repos/%s/%s/branches", username, repoName);

        Branch[] branches = restClient.get()
                .uri(uri)
                .retrieve()
                .body(Branch[].class);

        return Arrays.stream(branches)
                .map(b -> new BranchInfo(b.name(), b.commit().sha()))
                .toList();
    }
}
