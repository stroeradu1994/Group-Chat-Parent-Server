package com.groupchat.parentserver.service;

import com.groupchat.parentserver.dto.CreateMessageRequest;
import com.groupchat.parentserver.dto.MessageResponse;
import com.groupchat.parentserver.model.Message;
import com.groupchat.parentserver.model.Profile;
import com.groupchat.parentserver.repo.ChatRepo;
import com.groupchat.parentserver.repo.ProfileRepo;
import javassist.NotFoundException;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ChatServiceImplTest {

    @Mock
    ProfileRepo profileRepo;

    @Mock
    ChatRepo chatRepo;

    @InjectMocks
    ChatServiceImpl chatService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getSuccesfullMessage() {
        String messageId = "123";
        String senderId = "456";
        String messageText = "Salut";
        String sender = "Ion";

        Message message = new Message();
        message.setId(messageId);
        message.setSender(senderId);
        message.setMessage(messageText);

        Profile profile = new Profile();
        profile.setId(senderId);
        profile.setName(sender);
        profile.setOnline(false);

        when(chatRepo.findAll()).thenReturn(Arrays.asList(message));
        when(profileRepo.findById("456")).thenReturn(Optional.of(profile));

        List<MessageResponse> result = chatService.getMessages();

        verify(chatRepo).findAll();
        verify(profileRepo).findById(anyString());
        assertEquals(1, result.size());
        MessageResponse messageResponse = result.get(0);
        assertEquals("123", messageResponse.getId());
        assertEquals("Salut", messageResponse.getMessage());
        assertEquals("456", messageResponse.getSenderId());
        assertEquals("Ion", messageResponse.getSender());
    }

    @Test
    void getNoMessagesWhenThereAreNoMessagesInChatRepo() {
        when(chatRepo.findAll()).thenReturn(new ArrayList<>());

        List<MessageResponse> result = chatService.getMessages();

        assertEquals(0, result.size());
    }

    @Test
    void getUnknownSenderWhenNoProfileIsFound() {
        String messageId = "123";
        String senderId = "456";
        String messageText = "Salut";

        Message message = new Message();
        message.setId(messageId);
        message.setSender(senderId);
        message.setMessage(messageText);

        when(chatRepo.findAll()).thenReturn(Arrays.asList(message));
        when(profileRepo.findById("456")).thenReturn(Optional.empty());

        List<MessageResponse> result = chatService.getMessages();

        assertEquals(1, result.size());
        MessageResponse messageResponse = result.get(0);
        assertEquals("123", messageResponse.getId());
        assertEquals("Salut", messageResponse.getMessage());
        assertEquals("456", messageResponse.getSenderId());
        assertEquals("Unknown", messageResponse.getSender());
    }

    @Test
    void sendMessageAndSaveItInChatRepo() throws Exception {
        CreateMessageRequest createMessageRequest = new CreateMessageRequest();
        createMessageRequest.setSenderId("456");
        createMessageRequest.setMessage("Salut");

        Profile profile = new Profile();
        profile.setId("456");
        profile.setName("Ion");
        profile.setOnline(false);

        when(profileRepo.findById(createMessageRequest.getSenderId())).thenReturn(Optional.of(profile));
        when(chatRepo.findAll()).thenReturn(new ArrayList<>());

        chatService.sendMessage(createMessageRequest);

        ArgumentCaptor<Message> argument = ArgumentCaptor.forClass(Message.class);
        verify(chatRepo).save(argument.capture());
        assertEquals("Salut", argument.getValue().getMessage());
        assertEquals("456", argument.getValue().getSender());
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    void sendMessageWithUnknownSender() throws Exception {
        CreateMessageRequest createMessageRequest = new CreateMessageRequest();
        createMessageRequest.setSenderId("456");
        createMessageRequest.setMessage("Salut");

        String errorMessage = "Profile with id 456 was not found.";

        when(profileRepo.findById(anyString())).thenReturn(Optional.empty());

        expectedException.expect(NotFoundException.class);
        expectedException.expectMessage(errorMessage);

        chatService.sendMessage(createMessageRequest);
    }
}