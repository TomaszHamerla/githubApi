package com.example.githubapi.exceptionHandler;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
class ErrorResponse {
    private int status;
    private String message;
}
