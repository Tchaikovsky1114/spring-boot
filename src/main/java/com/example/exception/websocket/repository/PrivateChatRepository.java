package com.example.exception.websocket.repository;

import com.example.exception.websocket.model.PrivateChatRoomDTO;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Slf4j
public class PrivateChatRepository {

    private Map<String, PrivateChatRoomDTO> privateChatRoomMap;

    @PostConstruct
    public void init() {
        privateChatRoomMap = new HashMap<>();
    }

    public List<PrivateChatRoomDTO> findAllRoom() {
        List<PrivateChatRoomDTO> privateChatRooms = new ArrayList<>(privateChatRoomMap.values());
        Collections.reverse(privateChatRooms);

        return privateChatRooms;
    }
    public PrivateChatRoomDTO findByRoomId(String roomId) {
        return privateChatRoomMap.get(roomId);
    }
    public PrivateChatRoomDTO createPrivateChatRoom(String roomName) {
        PrivateChatRoomDTO privateChatRoom = new PrivateChatRoomDTO().create(roomName);
        privateChatRoomMap.put(privateChatRoom.getRoomId(), privateChatRoom);
        return privateChatRoom;
    }

    public void increaseUser(String roomId){
        PrivateChatRoomDTO chatRoom = privateChatRoomMap.get(roomId);
        chatRoom.setUserCount(chatRoom.getUserCount()+1);
    }

    // 채팅방 인원 -1
    public void decreaseUser(String roomId){
        PrivateChatRoomDTO chatRoom = privateChatRoomMap.get(roomId);
        chatRoom.setUserCount(chatRoom.getUserCount()-1);
    }

    public String addUser(String roomId, String userName) {
        PrivateChatRoomDTO privateChatRoom = privateChatRoomMap.get(roomId);
        String userUUID = UUID.randomUUID().toString();
        privateChatRoom.getUserList().put(userUUID, userName);

        return userUUID;
    }
    public void deleteUser(String roomId,String userUUID){
        PrivateChatRoomDTO privateChatRoom = privateChatRoomMap.get(roomId);
        privateChatRoom.getUserList().remove(userUUID);
    }

    // 채팅방 userName 조회
    public String getUserName(String roomId,String userUUID){
        PrivateChatRoomDTO privateChatRoom = privateChatRoomMap.get(roomId);

        return privateChatRoom.getUserList().get(userUUID);
    }

    //채팅방 전체 userList 조회
    public List<String> getUserList(String roomId){
        List<String> list = new ArrayList<>();

        PrivateChatRoomDTO privateChatRoom = privateChatRoomMap.get(roomId);

        privateChatRoom.getUserList().forEach((key,value) -> list.add(value));

        return list;
    }
}
