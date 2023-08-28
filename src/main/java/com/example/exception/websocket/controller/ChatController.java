package com.example.exception.websocket.controller;


import com.example.exception.model.ChatUserData;
import com.example.exception.websocket.model.ChatRoom;
import com.example.exception.websocket.model.Message;
import com.example.exception.websocket.service.ChatRoomManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
@Slf4j
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatRoomManager chatRoomManager;

    @MessageMapping("/chat/{roomId}")
    public void handleChatMessage(@DestinationVariable String roomId,
                                  @Payload Message message
                                  ) {
        messagingTemplate.convertAndSend("/topic/" + roomId, message);
    }


    @MessageMapping("/chat/{roomId}/join")
    @SendTo("/topic/{roomId}/join")
    public Map<String,ChatUserData> joinChatRoom(@DestinationVariable String roomId,
                                                 @Payload ChatUserData chatUserData
                                     ) {
        if(chatRoomManager.getChatRoom(roomId) == null) {
            chatRoomManager.createChatRoom(roomId);
        }
        chatRoomManager.addUserToChatRoom(roomId, chatUserData);
        return chatRoomManager.getChatRoom(roomId).getUsers();
    }

    @MessageMapping("/chat/{roomId}/changeId")
    public void changeUserId(@DestinationVariable String roomId,
                             @Payload ChatUserData chatUserData
    ) {
        chatRoomManager.changeUsername(roomId, chatUserData);
        ChatRoom chatRoom = chatRoomManager.getChatRoom(roomId);
        log.info("changeId chatRoom.getUsers() = {}", chatRoom.getUsers());
        messagingTemplate.convertAndSend("/topic/" + roomId + "/userlist", chatRoom.getUsers());
    }

    @MessageMapping("/chat/{roomId}/disconnect")
    public void leaveChatRoom(@DestinationVariable String roomId,
                              @Payload String token
                              ) {
        chatRoomManager.removeUserFromChatRoom(roomId, token );

//        log.info("disconnect roomId = {}",roomId);
//        log.info("disconnect userToken = {}", token);
//        log.info("remain user after disconnect someone = {}", chatRoomManager.getChatRoom(roomId));
        messagingTemplate.convertAndSend(
                "/topic/" + roomId + "/disconnect",
                chatRoomManager.getChatRoom(roomId).getUsers()
        );
    }

    @MessageMapping("/chat/whisper/{tokenId}")
    public void whisperChatting(
            @DestinationVariable String tokenId,
            @Payload Message message) {
        log.info("whisper message = {}", message);
        // message에 있는 token을 가지고 있는 사람에게만 보내기
        messagingTemplate.convertAndSendToUser(tokenId, "/queue/whisper" + tokenId, message);
    }
}
