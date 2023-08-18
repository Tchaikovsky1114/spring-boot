package com.example.exception.websocket.controller;


import com.example.exception.websocket.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class ChatController {
    @MessageMapping("/chat")
    @SendTo("/topic/public")
    public Message broadcast(@Payload Message message) {
        log.info("@MemberController, chat GET()");
        log.info("message = {}",message);
        return message;
    }
}
