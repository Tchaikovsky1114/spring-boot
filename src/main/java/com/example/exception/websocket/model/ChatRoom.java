package com.example.exception.websocket.model;

import com.example.exception.model.ChatUserData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@Slf4j
public class ChatRoom {

    private String roomId;

    private Map<String,ChatUserData> users = new HashMap<>();
    public ChatRoom(String roomId) {
        this.roomId = roomId;
    }
    public void removeUser(String token) {
        users.remove(token);
    }

    public void addUser(ChatUserData chatUserData) {
        users.put(chatUserData.getToken(), chatUserData);
    }

    public void changeUsername(String token, ChatUserData chatUserData) {
        ChatUserData user = users.get(token);
        log.info("user = {}", user);
        user.setName(chatUserData.getName());
        log.info("changeUser = {}", user);
        users.put(token, user);
    }

}
