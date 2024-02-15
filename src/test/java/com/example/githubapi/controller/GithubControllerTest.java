package com.example.githubapi.controller;

import com.example.githubapi.exceptionHandler.UserNotFoundException;
import com.example.githubapi.model.BranchInfo;
import com.example.githubapi.model.Owner;
import com.example.githubapi.model.RepositoryInfo;
import com.example.githubapi.service.GithubService;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class GithubControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    GithubService githubService;

    @Test
    void getUserRepositoriesWithUserExistsShouldReturnListOfRepositoryInfo() throws Exception {
        //given
        String username = "TomaszHamerla";
        List<RepositoryInfo> repositoryInfos = List.of(new RepositoryInfo(("githubApi"), new Owner("TomaszHamerla"),
                List.of(new BranchInfo("java-code-modernization", "6d339bd2eed46861049dd677459fe9a1d686566c"),
                        new BranchInfo("main", "f758fd45e29987051cd76b700a43f849fc606358"))));
        String json = """
                [
                {
                        "repositoryName": "githubApi",
                        "owner": {
                            "login": "TomaszHamerla"
                        },
                        "branches": [
                            {
                                "name": "java-code-modernization",
                                "lastCommitSha": "6d339bd2eed46861049dd677459fe9a1d686566c"
                            },
                            {
                                "name": "main",
                                "lastCommitSha": "f758fd45e29987051cd76b700a43f849fc606358"
                            }
                        ]
                    }                                
                ]
                """;
        //when
        when(githubService.getUserRepositories(username)).thenReturn(repositoryInfos);
        //then
        ResultActions resultActions = mockMvc.perform(get("/repositories/TomaszHamerla"))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
        JSONAssert.assertEquals(json, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

    @Test
    void getUserRepositoriesWithUserDoesNotExistShouldReturnNotFound() throws Exception {
        //given
        String username = "nonExistingUser";
        String json = """
                {
                    "status": 404,
                    "message": "Not Found"
                }
                """;
        //when
        when(githubService.getUserRepositories(username)).thenThrow(new UserNotFoundException("Not Found"));
        //then
        ResultActions resultActions = mockMvc.perform(get("/repositories/nonExistingUser"))
                .andExpect(status().isNotFound())
                .andExpect(content().json(json));
        JSONAssert.assertEquals(json, resultActions.andReturn().getResponse().getContentAsString(), false);
    }
}