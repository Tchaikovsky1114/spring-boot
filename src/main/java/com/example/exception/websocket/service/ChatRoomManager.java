package com.example.exception.websocket.service;

import com.example.exception.model.ChatUserData;
import com.example.exception.websocket.model.ChatRoom;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ChatRoomManager {
    private final Map<String, ChatRoom> chatRooms = new HashMap<>();
    private final Set<String> chatTokenList = new HashSet<>();

    public void createChatRoom(String roomId) {
        chatRooms.put(roomId, new ChatRoom(roomId));
    }

    public ChatRoom getChatRoom(String roomId) {
        return chatRooms.get(roomId);
    }

    public void addUserToChatRoom(String roomId, ChatUserData chatUserData) {
        ChatRoom chatRoom = chatRooms.get(roomId);
        if(chatRoom != null){
            chatRoom.addUser(chatUserData);
        }
    }

    public ChatRoom findChatRoom(String roomId) {
        return chatRooms.get(roomId);
    }

    public void removeUserFromChatRoom(String roomId, String token) {
        ChatRoom chatRoom = chatRooms.get(roomId);

        if(chatRoom != null) {
            chatRoom.removeUser(token);
        }
    }

    public void changeUsername(String roomId, ChatUserData chatUserData) {
        ChatRoom chatRoom = chatRooms.get(roomId);
        chatRoom.changeUsername(chatUserData.getToken(), chatUserData);
    }
}
