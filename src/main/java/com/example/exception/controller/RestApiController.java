package com.example.exception.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class RestApiController {

    @GetMapping(path= "")
    public void hello() {
        var list = List.of("hllo");

        var element = list.get(1);
        log.info("element : {}", element);
        throw new RuntimeException("runtime exception call!");
    }

    public static class RestApiBController {
    }
}
