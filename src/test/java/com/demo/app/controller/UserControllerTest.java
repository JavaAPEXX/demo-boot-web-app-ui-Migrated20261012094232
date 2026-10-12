package com.demo.app.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.demo.app.model.Document;
import com.demo.app.model.User;
import com.demo.app.service.DocService;
import com.demo.app.service.SecurityService;
import com.demo.app.service.UserService;
import com.demo.app.validator.UserValidator;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    private UserController controller;

    @Mock
    private UserService userService;

    @Mock
    private SecurityService securityService;

    @Mock
    private DocService docService;

    @Mock
    private UserValidator userValidator;

    @TempDir
    Path tempDir;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("Given valid user form when registration is successful then redirect to welcome page")
    public void givenValidUserForm_whenRegistrationIsSuccessful_thenRedirectToWelcomePage() throws Exception {
        // Arrange
        User userForm = new User("username", "password", "passwordConfirm");
        when(userService.save(userForm)).thenReturn(userForm);

        // Act
        mockMvc.perform(post("/registration")
                .flashAttr("userForm", userForm))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/welcome"));
    }

    @Test
    @DisplayName("Given invalid user form when registration fails then stay on registration page")
    public void givenInvalidUserForm_whenRegistrationFails_thenStayOnRegistrationPage() throws Exception {
        // Arrange
        User userForm = new User("username", "password", "passwordConfirm");
        userForm.setUsername(null);
        userValidator.validate(userForm, null);

        // Act
        mockMvc.perform(post("/registration")
                .flashAttr("userForm", userForm))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("userForm", "username"));
    }

    @Test
    @DisplayName("Given valid login credentials when login is successful then redirect to welcome page")
    public void givenValidLoginCredentials_whenLoginIsSuccessful_thenRedirectToWelcomePage() throws Exception {
        // Arrange
        User user = new User("username", "password", "passwordConfirm");
        when(securityService.findLoggedInUsername()).thenReturn("username");
        when(securityService.autologin(anyString(), anyString())).thenReturn(true);

        // Act
        mockMvc.perform(get("/login")
                .param("username", "username")
                .param("password", "password"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/welcome"));
    }

    @Test
    @DisplayName("Given invalid login credentials when login fails then stay on login page")
    public void givenInvalidLoginCredentials_whenLoginFails_thenStayOnLoginPage() throws Exception {
        // Arrange
        when(securityService.findLoggedInUsername()).thenReturn("username");
        when(securityService.autologin(anyString