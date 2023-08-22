package com.example.exception.websocket.service;

import com.example.exception.websocket.model.ChatRoom;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ChatRoomManager {
    private Map<String, ChatRoom> chatRooms = new HashMap<>();

    public void createChatRoom(String roomId) {
        chatRooms.put(roomId, new ChatRoom(roomId));
    }

    public ChatRoom getChatRoom(String roomId) {
        return chatRooms.get(roomId);
    }

    public void addUserToChatRoom(String roomId, String username) {
        ChatRoom chatRoom = chatRooms.get(roomId);
        if(chatRoom != null){
            chatRoom.addUser(username);
        }
    }
    public void removeUserFromChatRoom(String roomId, String username) {
        ChatRoom chatRoom = chatRooms.get(roomId);
        if(chatRoom != null) {
            chatRoom.removeUser(username);
        }
    }
}
