package com.groupchat.parentserver.controller;

import com.groupchat.parentserver.service.ConnectionService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/connection")
public class ConnectionController {

    @Autowired
    ConnectionService connectionService;

    @PostMapping("/connect/{id}")
    public ResponseEntity<?> connect(@PathVariable("id") String id) {
        try {
            connectionService.connect(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/disconnect/{id}")
    public ResponseEntity<?> disconnect(@PathVariable("id") String id) {
        try {
            connectionService.disconnect(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
