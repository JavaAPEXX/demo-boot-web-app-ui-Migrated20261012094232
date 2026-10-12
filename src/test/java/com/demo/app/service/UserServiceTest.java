package com.demo.app.service;

import com.demo.app.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Given a valid user, when save is called, then the user should be saved")
    public void givenValidUser_whenSave_thenReturnUser() {
        // Arrange
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("password");

        // Act
        userService.save(user);

        // Assert
        verify(userRepository, times(1)).save(user);
    }

    @Test
    @DisplayName("Given a null user, when save is called, then an IllegalArgumentException should be thrown")
    public void givenNullUser_whenSave_thenThrowIllegalArgumentException() {
        // Arrange
        User user = null;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> userService.save(user));
        verifyNoInteractions(userRepository);
    }

    @Test
    @DisplayName("Given a user with an existing username, when save is called, then a DuplicateUserException should be thrown")
    public void givenUserWithExistingUsername_whenSave_thenThrowDuplicateUserException() {
        // Arrange
        User user = new User();
        user.setUsername("existingUser");
        user.setPassword("password");

        // Mock behavior
        when(userRepository.findByUsername(user.getUsername())).thenReturn(new User());

        // Act & Assert
        assertThrows(DuplicateUserException.class, () -> userService.save(user));
        verify(userRepository, never()).save(user);
    }

    @Test
    @DisplayName("Given a user with an empty username, when save is called, then an IllegalArgumentException should be thrown")
    public void givenUserWithEmptyUsername_whenSave_thenThrowIllegalArgumentException() {
        // Arrange
        User user = new User();
        user.setUsername("");
        user.setPassword("password");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> userService.save(user));
        verifyNoInteractions(userRepository);
    }

    @Test
    @DisplayName("Given a user with a null password, when save is called, then an IllegalArgumentException should be thrown")
    public void givenUserWithNullPassword_whenSave_thenThrowIllegalArgumentException() {
        // Arrange
        User user = new User();
        user.setUsername("testUser");
        user.setPassword(null);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> userService.save(user));
        verifyNoInteractions(userRepository);
    }

    @Test
    @DisplayName("Given a user with a valid username and password, when findByUsername is called, then the user should be found")
    public void givenValidUsernameAndPassword_whenFindByUsername_thenReturnUser() {
        // Arrange
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("password");
        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);

        // Act
        User foundUser = userService.findByUsername(user.getUsername());

        // Assert
        assertNotNull(foundUser);
        assertEquals(user, foundUser);
        verify(userRepository, times(1)).findByUsername(user.getUsername());
    }

    @Test
    @DisplayName("Given a non-existing username, when findByUsername is called, then null should be returned")
    public void givenNonExistingUsername_when