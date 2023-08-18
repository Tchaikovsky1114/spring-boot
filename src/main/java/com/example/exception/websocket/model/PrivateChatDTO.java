package com.example.exception.websocket.model;

public class PrivateChatDTO {

    public enum MessageType {
        ENTER, TALK, LEAVE
    }
    private MessageType type;
    private String roomId;
    private String sender;
    private String message;
    private String time;

}
