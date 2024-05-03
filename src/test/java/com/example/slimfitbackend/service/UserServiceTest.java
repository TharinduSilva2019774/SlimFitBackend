package com.example.slimfitbackend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.slimfitbackend.exceptions.CustomException;
import com.example.slimfitbackend.model.User;
import com.example.slimfitbackend.model.WeightProgress;
import com.example.slimfitbackend.payload.GetUserResponse;
import com.example.slimfitbackend.payload.SaveUserWeightRequest;
import com.example.slimfitbackend.payload.UserWeightResponse;
import com.example.slimfitbackend.payload.common.MapStructMapper;
import com.example.slimfitbackend.repository.UserRepository;
import com.example.slimfitbackend.repository.WeightProgressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

class UserServiceTest {

    @Mock
    private MapStructMapper mapStructMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private WeightProgressRepository weightProgressRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void testGetCurrentUser() {
        // Arrange
        User user = new User();
        user.setEmail("test@example.com");

        UserService userServiceSpy = spy(userService);
        doReturn(user).when(userServiceSpy).getCurrentUser();

        // Act
        User currentUser = userServiceSpy.getCurrentUser();

        // Assert
        assertNotNull(currentUser);
        assertEquals(user.getEmail(), currentUser.getEmail());
        verify(userServiceSpy, times(1)).getCurrentUser();
        verifyNoMoreInteractions(userServiceSpy);
    }

    @Test
    void testGetUser() {
        // Arrange
        User user = new User();
        user.setEmail("test@example.com");

        GetUserResponse expectedResponse = new GetUserResponse();
        expectedResponse.setEmail("test@example.com");

        UserService userServiceSpy = spy(userService);
        doReturn(user).when(userServiceSpy).getCurrentUser();
        when(mapStructMapper.userToGetUserResponse(user)).thenReturn(expectedResponse);

        // Act
        GetUserResponse response = userServiceSpy.getUser();

        // Assert
        assertNotNull(response);
        assertEquals(expectedResponse.getEmail(), response.getEmail());
        verify(userServiceSpy, times(1)).getCurrentUser();
        verify(mapStructMapper, times(1)).userToGetUserResponse(user);
    }

    @Test
    void testGetUserWeightResponse() {
        // Arrange
        User user = new User();
        user.setEmail("test@example.com");

        WeightProgress weightProgress = new WeightProgress();
        weightProgress.setWeight(70);

        UserService userServiceSpy = spy(userService);
        doReturn(user).when(userServiceSpy).getCurrentUser();
        when(userServiceSpy.getUserWeight()).thenReturn(weightProgress);

        // Act
        UserWeightResponse response = userServiceSpy.getUserWeightResponse();

        // Assert
        assertNotNull(response);
        assertEquals(70, response.getWeight());
    }

    @Test
    void testGetUserWeight() {
        // Arrange
        User user = new User();
        user.setEmail("test@example.com");

        WeightProgress weightProgress = new WeightProgress();
        weightProgress.setWeight(70);

        UserService userServiceSpy = spy(userService);
        doReturn(user).when(userServiceSpy).getCurrentUser();
        when(weightProgressRepository.findAllByUserOrderByDate(user)).thenReturn(List.of(weightProgress));

        // Act
        WeightProgress result = userServiceSpy.getUserWeight();

        // Assert
        assertNotNull(result);
        assertEquals(70, result.getWeight());
        verify(userServiceSpy, times(1)).getCurrentUser();
        verify(weightProgressRepository, times(1)).findAllByUserOrderByDate(user);
    }

    @Test
    void testSaveUserWeight() throws Exception {
        // Arrange
        SaveUserWeightRequest saveUserWeightRequest = new SaveUserWeightRequest();
        saveUserWeightRequest.setWeight(70);

        User user = new User();
        user.setEmail("test@example.com");

        UserService userServiceSpy = spy(userService);
        doReturn(user).when(userServiceSpy).getCurrentUser();

        // Act
        UserWeightResponse response = userServiceSpy.saveUserWeight(saveUserWeightRequest);

        // Assert
        assertNotNull(response);
        assertEquals(70, response.getWeight());
        verify(userServiceSpy, times(1)).getCurrentUser();
        verify(weightProgressRepository, times(1)).save(any(WeightProgress.class));
    }

    @Test
    void testSaveUserWeight_ThrowsException() {
        // Arrange
        SaveUserWeightRequest saveUserWeightRequest = new SaveUserWeightRequest();
        saveUserWeightRequest.setWeight(70);

        UserService userServiceSpy = spy(userService);
        doThrow(CustomException.class).when(userServiceSpy).getCurrentUser();

        // Act & Assert
        assertThrows(CustomException.class, () -> userServiceSpy.saveUserWeight(saveUserWeightRequest));
        verify(userServiceSpy, times(1)).getCurrentUser();
        verifyNoInteractions(weightProgressRepository);
    }
}
