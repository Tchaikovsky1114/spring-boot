package com.example.exception.controller;


import com.example.exception.model.Api;
import com.example.exception.model.UserRegisterRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v2")
public class UserBApiController {
    @PostMapping("/user")
    public Api<UserRegisterRequest> register(
        @Valid
        @RequestBody
        Api<UserRegisterRequest> userRegisterRequest
    ){
        log.info("init, {}", userRegisterRequest);

        var body = userRegisterRequest.getData();

        Api<UserRegisterRequest> response =
                Api.<UserRegisterRequest>builder()
                        .resultCode(String.valueOf(HttpStatus.OK.value()))
                        .resultMessage(HttpStatus.OK.getReasonPhrase())
                        .data(body)
                        .build();
        return response;
    }
}

//