package com.example.exception.websocket.controller;


import com.example.exception.websocket.model.ChatRoom;
import com.example.exception.websocket.model.Message;
import com.example.exception.websocket.model.Userlist;
import com.example.exception.websocket.service.ChatRoomManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatRoomManager chatRoomManager;

    private final List<String> userList = new ArrayList<>();


    @MessageMapping("/chat/{roomId}")
    public void handleChatMessage(@DestinationVariable String roomId,
                                  @Payload Message message
                                  ) {

//        log.info("@MemberController, chat/{roomId} GET()");
//        log.info("message = {}",message);
        messagingTemplate.convertAndSend("/topic/" + roomId, message);
    }

    @MessageMapping("/chat/{roomId}/changeId")
    public void changeUserId(@DestinationVariable String roomId,
                             @Payload Userlist userlist
                             ) {


        ChatRoom chatRoom = chatRoomManager.getChatRoom(roomId);
        chatRoom.changeUsername(userlist.getPrevUser(), userlist.getCurrentUser());
//        log.info("@MemberController, chat/{roomId}/changeId");
//        log.info("current chatRoom user = {}",chatRoom.getUsers());
//        log.info("current chatRoom roomId = {}",roomId);
//        System.out.println("/topic/" + roomId + "/userlist");
        messagingTemplate.convertAndSend("/topic/" + roomId + "/userlist", chatRoom.getUsers());
    }
    @MessageMapping("/chat/{roomId}/join")
    @SendTo("/topic/{roomId}/join")
    public List<String> joinChatRoom(@DestinationVariable String roomId,
                                     @Payload String username
                                     ) {
        if(chatRoomManager.getChatRoom(roomId) == null) {
            chatRoomManager.createChatRoom(roomId);
        }

        chatRoomManager.addUserToChatRoom(roomId, username);
//        headerAccessor.getSessionAttributes().put("username", username);
        return chatRoomManager.getChatRoom(roomId).getUsers();
    }

    @MessageMapping("/chat/{roomId}/disconnect")
    public void leaveChatRoom(@DestinationVariable String roomId,
                              @Payload String username
                              ) {
        chatRoomManager.removeUserFromChatRoom(roomId, username);

        log.info("disconnect roomId = {}",roomId);
        log.info("disconnect username = {}", username);

        messagingTemplate.convertAndSend(
                "/topic/" + roomId + "/disconnect",
                chatRoomManager.getChatRoom(roomId).getUsers()
        );
    }
}
