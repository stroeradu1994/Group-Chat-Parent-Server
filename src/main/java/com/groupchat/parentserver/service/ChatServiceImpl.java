package com.groupchat.parentserver.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.groupchat.parentserver.dto.CreateMessageRequest;
import com.groupchat.parentserver.dto.MessageResponse;
import com.groupchat.parentserver.model.Message;
import com.groupchat.parentserver.model.Profile;
import com.groupchat.parentserver.repo.ChatRepo;
import com.groupchat.parentserver.repo.ProfileRepo;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    ProfileRepo profileRepo;

    @Autowired
    ChatRepo chatRepo;

    public List<MessageResponse> sendMessage(@RequestBody CreateMessageRequest createMessageRequest) throws NotFoundException {
        Optional<Profile> profileOptional = profileRepo.findById(createMessageRequest.getSenderId());
        if (profileOptional.isPresent()) {
            Message message = new Message();
            message.setMessage(createMessageRequest.getMessage());
            message.setSender(createMessageRequest.getSenderId());
            message.setCreatedAt(LocalDateTime.now());
            chatRepo.save(message);
            return getMessages();
        } else {
            throw new NotFoundException("Profile with id " + createMessageRequest.getSenderId() + " was not found.");
        }
    }

    public List<MessageResponse> getMessages() {
        return Lists.newArrayList(chatRepo.findAll())
                .stream()
                .map(message -> profileRepo.findById(message.getSender())
                        .map(profile -> createMessageResponse(message, profile.getName()))
                        .orElseGet(() -> createMessageResponse(message, "Unknown")))
                .sorted(Comparator.comparing(MessageResponse::getCreatedAt))
                .collect(Collectors.toList());
    }

    private MessageResponse createMessageResponse(Message message, String senderName) {
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setCreatedAt(message.getCreatedAt());
        messageResponse.setId(message.getId());
        messageResponse.setMessage(message.getMessage());
        messageResponse.setSenderId(message.getSender());
        messageResponse.setSender(senderName);
        return messageResponse;
    }
}
