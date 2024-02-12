package com.example.githubapi.service;

import com.example.githubapi.configuration.ConfigProperties;
import com.example.githubapi.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class GithubServiceImplTest {
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private ConfigProperties prop;
    @InjectMocks
    private GithubServiceImpl githubService;
    private String username;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(prop.getGithubBaseUrl()).thenReturn("/example");
        username = "example";
    }

    @Test
    void getUserRepositories_withUsernameExists_returnsRepositories() {
        //given
        GithubResponse[] response = getResponse();
        Branch[] branchResponse = getBranchResponse();
        when(restTemplate.getForObject(anyString(), eq(GithubResponse[].class))).thenReturn(response);
        when(restTemplate.getForObject(anyString(), eq(Branch[].class))).thenReturn(branchResponse);

        //when
        List<RepositoryInfo> repositories = githubService.getUserRepositories(username);

        //then
        assertNotNull(repositories);
        assertEquals(repositories.get(0).getRepositoryName(), "NameExample");
    }

    @Test
    void getUserRepositories_withUserNoExists_throwsHttpClientErrorExceptionNotFound() {
        //given
        when(restTemplate.getForObject(anyString(), eq(GithubResponse[].class))).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        //when
        Exception exception = assertThrows(HttpClientErrorException.class, () -> githubService.getUserRepositories(username));

        //then
        assertThat(exception).isInstanceOf(HttpClientErrorException.class);
    }

    private Branch[] getBranchResponse() {
        Commit commit = new Commit();
        commit.setSha("exampleSha");
        Branch branch = new Branch();
        branch.setName("exampleBranch");
        branch.setCommit(commit);
        return new Branch[]{branch};
    }

    private GithubResponse[] getResponse() {
        Owner owner = new Owner();
        owner.setLogin("OwnerExample");
        GithubResponse githubResponse = new GithubResponse();
        githubResponse.setFork(false);
        githubResponse.setOwner(owner);
        githubResponse.setName("NameExample");

        Owner owner1 = new Owner();
        owner1.setLogin("OwnerExample1");
        GithubResponse githubResponse1 = new GithubResponse();
        githubResponse1.setFork(false);
        githubResponse1.setOwner(owner1);
        githubResponse1.setName("NameExample1");

        Owner owner2 = new Owner();
        owner2.setLogin("OwnerExample2");
        GithubResponse githubResponse2 = new GithubResponse();
        githubResponse2.setFork(false);
        githubResponse2.setOwner(owner2);
        githubResponse2.setName("NameExample2");

        return new GithubResponse[]{githubResponse, githubResponse1, githubResponse2};
    }
}