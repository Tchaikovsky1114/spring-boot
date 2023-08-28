package com.example.exception.controller;


import com.example.exception.model.Api;
import com.example.exception.model.TokenResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/chat")
public class ChatApiController {

    @GetMapping("/token")
    public Api<TokenResponse> getChatToken() {
        TokenResponse token = TokenResponse.builder()
                .chatToken(UUID.randomUUID())
                .build();
        
        return Api.<TokenResponse>builder()
                .resultCode(String.valueOf(HttpStatus.OK.value()))
                .resultMessage(HttpStatus.OK.name())
                .data(token)
                .build();
    }
}
