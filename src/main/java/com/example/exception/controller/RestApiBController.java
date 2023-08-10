package com.example.exception.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/b")
public class RestApiBController {

    @GetMapping("/hello")
    public void hello() {
        throw new NumberFormatException("number format exception");
    }
//    global exception이 아닌 해당 api의 exception을 명시화해서 처리하기 위한 exceptionHandler
    @ExceptionHandler(value = { NumberFormatException.class })
    public ResponseEntity numberFormatException(
            NumberFormatException e
    ){
        log.error("=== api/b를 사용하는 controller에서 발생한 error===", e);
        return ResponseEntity.ok().build();
    }
}
