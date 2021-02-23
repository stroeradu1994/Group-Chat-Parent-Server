package com.groupchat.parentserver.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageResponse {

    private String id;

    private String senderId;

    private String sender;

    private LocalDateTime createdAt;

    private String message;

}
