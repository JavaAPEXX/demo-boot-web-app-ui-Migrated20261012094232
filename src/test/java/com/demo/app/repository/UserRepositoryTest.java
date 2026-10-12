package com.demo.app.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.demo.app.model.User;
import com.demo.app.repository.UserRepository;
import com.demo.app.service.UserService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {

    @InjectMocks
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Given existing user, when findByUsername is called, then the user is returned")
    public void givenExistingUser_whenFindByUsername_thenReturnUser() {
        // Arrange
        String username = "existingUser";
        User user = new User();
        user.setUsername(username);
        when(userService.getUserByUsername(username)).thenReturn(user);

        // Act
        User result = userRepository.findByUsername(username);

        // Assert
        assertNotNull(result);
        assertEquals(username, result.getUsername());
        verify(userService).getUserByUsername(username);
    }

    @Test
    @DisplayName("Given non-existing user, when findByUsername is called, then a UserNotFoundException is thrown")
    public void givenNonExistingUser_whenFindByUsername_thenThrowUserNotFoundException() {
        // Arrange
        String username = "nonExistingUser";
        when(userService.getUserByUsername(username)).thenThrow(new UserNotFoundException());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userRepository.findByUsername(username));
        verify(userService).getUserByUsername(username);
    }

    @Test
    @DisplayName("Given null username, when findByUsername is called, then a null is returned")
    public void givenNullUsername_whenFindByUsername_thenReturnNull() {
        // Arrange
        String username = null;
        when(userService.getUserByUsername(username)).thenReturn(null);

        // Act
        User result = userRepository.findByUsername(username);

        // Assert
        assertNull(result);
        verify(userService).getUserByUsername(username);
    }

    @Test
    @DisplayName("Given empty username, when findByUsername is called, then a null is returned")
    public void givenEmptyUsername_whenFindByUsername_thenReturnNull() {
        // Arrange
        String username = "";
        when(userService.getUserByUsername(username)).thenReturn(null);

        // Act
        User result = userRepository.findByUsername(username);

        // Assert
        assertNull(result);
        verify(userService).getUserByUsername(username);
    }
}