package com.example.slimfitbackend.service;

import com.example.slimfitbackend.model.Message;
import com.example.slimfitbackend.model.User;
import com.example.slimfitbackend.payload.MessageResponse;
import com.example.slimfitbackend.payload.SendMessageRequest;
import com.example.slimfitbackend.payload.common.MapStructMapper;
import com.example.slimfitbackend.repository.MessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MessageServiceTest {

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private MapStructMapper mapStructMapper;

    @Mock
    private UserService userService;

    @InjectMocks
    private MessageService messageService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllMessageResponse() {
        // Mocking data
        Message message1 = new Message();
        message1.setId(1L);
        message1.setMessage("Test message 1");
        User user1 = new User();
        user1.setUserId(1L);
        user1.setFirstName("John");
        message1.setUser(user1);

        MessageResponse messageResponse1 = new MessageResponse();
        messageResponse1.setId(1L);
        messageResponse1.setMessage("Test message 1");
        messageResponse1.setUsername("John");

        List<Message> messages = new ArrayList<>();
        messages.add(message1);

        List<MessageResponse> expectedResponse = new ArrayList<>();
        expectedResponse.add(messageResponse1);

        // Mocking repository behavior
        when(messageRepository.findAll()).thenReturn(messages);
        when(mapStructMapper.messageToMessageResponse(message1)).thenReturn(messageResponse1);

        // Calling the service method
        List<MessageResponse> actualResponse = messageService.getAllMessageResponse();

        // Verifying the result
        assertEquals(expectedResponse.size(), actualResponse.size());
        assertEquals(expectedResponse.get(0).getId(), actualResponse.get(0).getId());
        assertEquals(expectedResponse.get(0).getMessage(), actualResponse.get(0).getMessage());
        assertEquals(expectedResponse.get(0).getUsername(), actualResponse.get(0).getUsername());

        // Verifying interactions with mocks
        verify(messageRepository, times(1)).findAll();
        verify(mapStructMapper, times(1)).messageToMessageResponse(message1);
    }

    @Test
    public void testSendMessage() throws Exception {
        // Mocking data
        SendMessageRequest request = new SendMessageRequest();
        request.setMessage("Test message");

        User user = new User();
        user.setUserId(1L);
        user.setFirstName("John");

        Message message = new Message();
        message.setId(1L);
        message.setMessage("Test message");
        message.setUser(user);
        message.setDate(new Date());

        MessageResponse expectedResponse = new MessageResponse();
        expectedResponse.setId(1L);
        expectedResponse.setMessage("Test message");
        expectedResponse.setUsername("John");

        // Mocking getCurrentUser() method of UserService
        when(userService.getCurrentUser()).thenReturn(user);

        // Mocking repository behavior
        when(messageRepository.save(any(Message.class))).thenReturn(message);
        when(mapStructMapper.messageToMessageResponse(message)).thenReturn(expectedResponse);

        // Calling the service method
        MessageResponse actualResponse = messageService.sendMessage(request);

        // Verifying the result
        assertEquals(expectedResponse.getId(), actualResponse.getId());
        assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        assertEquals(expectedResponse.getUsername(), actualResponse.getUsername());

        // Verifying interactions with mocks
        verify(userService, times(1)).getCurrentUser();
        verify(messageRepository, times(1)).save(any(Message.class));
        verify(mapStructMapper, times(1)).messageToMessageResponse(message);
    }
}
