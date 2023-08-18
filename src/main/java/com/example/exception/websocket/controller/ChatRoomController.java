package com.example.exception.websocket.controller;

import com.example.exception.websocket.model.PrivateChatRoomDTO;
import com.example.exception.websocket.repository.PrivateChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ChatRoomController {

    private final PrivateChatRepository repository;

    @GetMapping("/")
    public String ChatRoomList(Model model) {
        model.addAttribute("list",repository.findAllRoom());
        log.info("Show All ChatList: {}", repository.findAllRoom());
        return "roomList";
    }

    @PostMapping("/chat/createroom")
    public String createRoom(@RequestParam String roomName, RedirectAttributes rttr) {
        PrivateChatRoomDTO privateChatRoom = repository.createPrivateChatRoom(roomName);
        log.info("Create ChatRoom: {}", privateChatRoom);
        rttr.addFlashAttribute("roomName",privateChatRoom);

        return "redirect:/";
    }

    @GetMapping("/chat/joinroom")
    public String joinRoom(String roomId, Model model) {
        log.info("roomId: {}", roomId);
        model.addAttribute("room", repository.findByRoomId(roomId));

        return "chatroom";
    }
}
