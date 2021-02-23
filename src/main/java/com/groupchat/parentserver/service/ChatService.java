package com.groupchat.parentserver.service;

import com.groupchat.parentserver.dto.CreateMessageRequest;
import com.groupchat.parentserver.dto.MessageResponse;
import javassist.NotFoundException;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;

public interface ChatService {

    List<MessageResponse> sendMessage(@RequestBody CreateMessageRequest createMessageRequest) throws NotFoundException;

    List<MessageResponse> getMessages();
}
