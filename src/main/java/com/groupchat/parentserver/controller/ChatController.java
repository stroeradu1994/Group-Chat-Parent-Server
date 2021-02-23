package com.groupchat.parentserver.controller;

import com.groupchat.parentserver.dto.CreateMessageRequest;
import com.groupchat.parentserver.service.ChatService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    ChatService chatService;

    @PostMapping("/")
    public ResponseEntity<?> sendMessage(@RequestBody CreateMessageRequest createMessageRequest) {
        try {
            return ResponseEntity.ok(chatService.sendMessage(createMessageRequest));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getMessages() {
        return ResponseEntity.ok(chatService.getMessages());
    }

}
