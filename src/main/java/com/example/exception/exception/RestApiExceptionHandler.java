package com.example.exception.exception;
import com.example.exception.controller.RestApiBController;
import com.example.exception.controller.RestApiController;
import com.example.exception.model.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice(basePackageClasses = { RestApiController.class, RestApiBController.class }) // 복수클래스로 범위 지정
@Order(1) //음수에 가까울수록 순서가 낮음
//@RestControllerAdvice(basePackages = "com.example.exception.controller") 패키지로 범위 지정
//@RestControllerAdvice(basePackageClasses = {RestApiBController.class}) 클래스로 범위 지정
//@ORDER(1) ExceptionHandler Class들의 실행 순서를 정할 수 있다.
public class RestApiExceptionHandler {

    @ExceptionHandler(value = { IndexOutOfBoundsException.class })
    public ResponseEntity outOfBound(
            IndexOutOfBoundsException e
    ) {
        log.error("IndexOutOfBoundsException", e);
        return ResponseEntity.status(200).build();
    }

    @ExceptionHandler(value ={NoSuchElementException.class})
    public ResponseEntity<Api> noSuchElement(
            NoSuchElementException e
    ) {
        log.error("찾으시려는 아이템이 존재하지 않습니다.",e);

        var response =  Api.builder()
                .resultCode(String.valueOf(HttpStatus.NOT_FOUND.value()))
                .resultMessage(HttpStatus.NOT_FOUND.getReasonPhrase())
                .build();

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

}