package com.demo.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class WebApplicationTest {

    @InjectMocks
    private WebApplication webApplication;

    @MockBean
    private SomeDependency someDependency;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Given null input when configure is called then throw IllegalArgumentException")
    public void givenNullInput_whenConfigure_thenThrowIllegalArgumentException() {
        // Arrange
        String input = null;

        // Act
        assertThrows(IllegalArgumentException.class, () -> webApplication.configure(input));

        // Assert
        verify(someDependency, never()).someMethod();
    }

    @Test
    @DisplayName("Given valid input when configure is called then no exception is thrown")
    public void givenValidInput_whenConfigure_thenNoExceptionIsThrown() {
        // Arrange
        String input = "validInput";

        // Act
        webApplication.configure(input);

        // Assert
        verify(someDependency, times(1)).someMethod();
    }

    @Test
    @DisplayName("Given empty input when configure is called then no exception is thrown")
    public void givenEmptyInput_whenConfigure_thenNoExceptionIsThrown() {
        // Arrange
        String input = "";

        // Act
        webApplication.configure(input);

        // Assert
        verify(someDependency, times(1)).someMethod();
    }

    @Test
    @DisplayName("Given null input when main is called then throw IllegalArgumentException")
    public void givenNullInput_whenMain_thenThrowIllegalArgumentException() {
        // Arrange
        String[] args = null;

        // Act
        assertThrows(IllegalArgumentException.class, () -> WebApplication.main(args));

        // Assert
        verifyNoInteractions(someDependency);
    }

    @Test
    @DisplayName("Given valid input when main is called then no exception is thrown")
    public void givenValidInput_whenMain_thenNoExceptionIsThrown() {
        // Arrange
        String[] args = {"arg1", "arg2"};

        // Act
        WebApplication.main(args);

        // Assert
        verifyNoInteractions(someDependency);
    }
}