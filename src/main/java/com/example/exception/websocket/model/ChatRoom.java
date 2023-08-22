package com.example.exception.websocket.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ChatRoom {

    private String roomId;
    private List<String> users = new ArrayList<>();

    public ChatRoom(String roomId) {
        this.roomId = roomId;
    }
    public void removeUser(String username) {
        users.remove(username);
    }

    public void addUser(String username) {
        users.add(username);
    }

    public void changeUsername(String prevUser, String currentUser) {
        int index = users.indexOf(prevUser);
        if(index > 0) {
            users.set(index, currentUser);
        } else {
            users.add(currentUser);
        }
    }

}
