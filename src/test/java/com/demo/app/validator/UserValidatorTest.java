package com.demo.app.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import com.demo.app.model.User;
import com.demo.app.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserValidatorTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserValidator userValidator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Given a valid user with valid credentials, when validate is called, then no errors should be returned")
    public void givenValidUserWithValidCredentials_whenValidate_thenNoErrors() {
        // Arrange
        User user = new User();
        user.setUsername("validUsername");
        user.setPassword("validPassword");
        user.setPasswordConfirm("validPassword");

        // Act
        userValidator.validate(user, mock(Errors.class));

        // Assert
        verifyNoInteractions(userService);
    }

    @Test
    @DisplayName("Given a user with an empty username, when validate is called, then an error should be returned for username")
    public void givenUserWithEmptyUsername_whenValidate_thenErrorForUsername() {
        // Arrange
        User user = new User();
        user.setUsername("");
        user.setPassword("validPassword");
        user.setPasswordConfirm("validPassword");

        // Act
        userValidator.validate(user, mock(Errors.class));

        // Assert
        verifyNoInteractions(userService);
        assertEquals(1, mock(Errors.class).getErrorCount());
        assertEquals("NotEmpty", mock(Errors.class).getErrorForField("username").getCode());
    }

    @Test
    @DisplayName("Given a user with a username that is too short, when validate is called, then an error should be returned for username")
    public void givenUserWithTooShortUsername_whenValidate_thenErrorForUsername() {
        // Arrange
        User user = new User();
        user.setUsername("a");
        user.setPassword("validPassword");
        user.setPasswordConfirm("validPassword");

        // Act
        userValidator.validate(user, mock(Errors.class));

        // Assert
        verifyNoInteractions(userService);
        assertEquals(1, mock(Errors.class).getErrorCount());
        assertEquals("Size.userForm.username", mock(Errors.class).getErrorForField("username").getCode());
    }

    @Test
    @DisplayName("Given a user with a username that is too long, when validate is called, then an error should be returned for username")
    public void givenUserWithTooLongUsername_whenValidate_thenErrorForUsername() {
        // Arrange
        User user = new User();
        user.setUsername("a".repeat(33));
        user.setPassword("validPassword");
        user.setPasswordConfirm("validPassword");

        // Act
        userValidator.validate(user, mock(Errors.class));

        // Assert
        verifyNoInteractions(userService);
        assertEquals(1, mock(Errors.class).getErrorCount());
        assertEquals("Size.userForm.username", mock(Errors.class).getErrorForField("username").getCode());
    }

    @Test
    @DisplayName("Given a user with a username that already exists, when validate is called, then an error should be returned for username")
    public void givenUserWithExistingUsername_whenValidate_thenErrorForUsername() {
        // Arrange
        User user = new User();
        user.setUsername("existingUsername");
        user.setPassword("validPassword");
        user.setPasswordConfirm("validPassword");

        when(userService.findByUsername(user.getUsername())).thenReturn