package com.demo.app.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.demo.app.repository.UserRepository;
import com.demo.app.model.User;

@ExtendWith(MockitoExtension.class)
public class SecurityServiceTest {

    @InjectMocks
    private SecurityService securityService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("givenNullUsername_whenFindLoggedInUsername_thenThrowNullPointerException")
    public void givenNullUsername_whenFindLoggedInUsername_thenThrowNullPointerException() {
        // Arrange
        String username = null;

        // Act & Assert
        assertThrows(NullPointerException.class, () -> securityService.findLoggedInUsername());
    }

    @Test
    @DisplayName("givenEmptyUsername_whenFindLoggedInUsername_thenThrowNullPointerException")
    public void givenEmptyUsername_whenFindLoggedInUsername_thenThrowNullPointerException() {
        // Arrange
        String username = "";

        // Act & Assert
        assertThrows(NullPointerException.class, () -> securityService.findLoggedInUsername());
    }

    @Test
    @DisplayName("givenValidUsername_whenFindLoggedInUsername_thenReturnUsername")
    public void givenValidUsername_whenFindLoggedInUsername_thenReturnUsername() {
        // Arrange
        String username = "testUser";
        User user = new User();
        user.setUsername(username);
        when(userRepository.findByUsername(username)).thenReturn(user);

        // Act
        String result = securityService.findLoggedInUsername();

        // Assert
        assertEquals(username, result);
        verify(userRepository).findByUsername(username);
    }

    @Test
    @DisplayName("givenNonExistingUsername_whenFindLoggedInUsername_thenThrowUserNotFoundException")
    public void givenNonExistingUsername_whenFindLoggedInUsername_thenThrowUserNotFoundException() {
        // Arrange
        String username = "nonExistingUser";

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> securityService.findLoggedInUsername());
        verify(userRepository).findByUsername(username);
    }

    @Test
    @DisplayName("givenValidUsernameAndPassword_whenAutologin_thenReturnVoid")
    public void givenValidUsernameAndPassword_whenAutologin_thenReturnVoid() {
        // Arrange
        String username = "testUser";
        String password = "testPassword";

        // Act
        securityService.autologin(username, password);

        // Assert
        verify(userRepository).findByUsername(username);
    }

    @Test
    @DisplayName("givenInvalidUsernameAndPassword_whenAutologin_thenThrowAuthenticationException")
    public void givenInvalidUsernameAndPassword_whenAutologin_thenThrowAuthenticationException() {
        // Arrange
        String username = "invalidUser";
        String password = "invalidPassword";

        // Act & Assert
        assertThrows(AuthenticationException.class, () -> securityService.autologin(username, password));
        verify(userRepository).findByUsername(username);
    }
}