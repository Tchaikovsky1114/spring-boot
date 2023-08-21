package com.example.exception.websocket.controller;


import com.example.exception.websocket.model.Message;
import com.example.exception.websocket.model.Userlist;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class ChatController {

    private final List<String> userList = new ArrayList<>();
    @MessageMapping("/chat")
    @SendTo("/topic/public")
    public Message broadcast(@Payload Message message) {
        log.info("@MemberController, chat GET()");
        log.info("message = {}",message);
        return message;
    }
    @MessageMapping("/chat/userlist")
    @SendTo("/topic/public/userlist")
    public List<String> addUser(@Payload Userlist userlist) {
        userList.add(userlist.getCurrentUser());
        userList.remove(userlist.getPrevUser());
        log.info("userList = {}",userList);
        return userList;
    }
    @MessageMapping("/chat/userlist/disconnect")
    @SendTo("/topic/public/userlist")
    public List<String> deleteUser(@Payload String username) {
        userList.remove(username);
        return userList;
    }
}
