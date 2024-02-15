package com.example.githubapi.service;

import com.example.githubapi.configuration.ConfigProperties;
import com.example.githubapi.exceptionHandler.UserNotFoundException;
import com.example.githubapi.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withResourceNotFound;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(GithubServiceImpl.class)
class GithubServiceImplTest {
    @Autowired
    MockRestServiceServer server;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    GithubServiceImpl githubService;
    @MockBean
    ConfigProperties configProperties;

    @Test
    void getUserRepositoriesWithUserExistsShouldReturnsListOfRepositoryInfo() throws JsonProcessingException {
        //given
        RepositoryInfo repositories =
                new RepositoryInfo("repo1", new Owner("user1"), List.of(new BranchInfo("branch1", "sha1")));
        List<GithubResponse> githubResponses = List.of(new GithubResponse("repo1", new Owner("user1"), false));
        List<Branch> branches = List.of(new Branch("branch1", new Commit("sha1")));

        //when
        server.expect(requestTo("/users/user1/repos"))
                .andRespond(withSuccess(objectMapper.writeValueAsString(githubResponses), MediaType.APPLICATION_JSON));
        server.expect(requestTo("/repos/user1/repo1/branches"))
                .andRespond(withSuccess(objectMapper.writeValueAsString(branches), MediaType.APPLICATION_JSON));

        //then
        List<RepositoryInfo> userRepositories = githubService.getUserRepositories("user1");
        assertThat(userRepositories.getFirst()).isEqualTo(repositories);
    }

    @Test
    void getUserRepositoriesWithUserDoesNotExistShouldThrowUserNotFoundException() {
        //when
        server.expect(requestTo("/users/user1/repos"))
                .andRespond(withResourceNotFound());

        //then
        assertThrows(UserNotFoundException.class, () -> githubService.getUserRepositories("user1"));
    }
}