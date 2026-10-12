package com.demo.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.demo.app.model.Document;
import com.demo.app.repository.DocRepository;

@ExtendWith(MockitoExtension.class)
public class DocServiceImplTest {

    @Mock
    private DocRepository docRepository;

    @InjectMocks
    private DocServiceImpl docService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Given a valid user ID, when findAllDocs is called, then return the user's documents")
    public void givenValidUserId_whenFindAllDocs_thenReturnUserDocs() {
        // Arrange
        Long userId = 1L;
        List<Document> expectedDocs = List.of(new Document(), new Document());
        when(docRepository.findUserDocs(userId)).thenReturn(expectedDocs);

        // Act
        List<Document> result = docService.findAllDocs(userId);

        // Assert
        assertEquals(expectedDocs, result);
    }

    @Test
    @DisplayName("Given a non-existing user ID, when findAllDocs is called, then throw UserNotFoundException")
    public void givenNonExistingUserId_whenFindAllDocs_thenThrowUserNotFoundException() {
        // Arrange
        Long userId = 999L;
        when(docRepository.findUserDocs(userId)).thenThrow(new UserNotFoundException("User not found"));

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> docService.findAllDocs(userId));
    }

    @Test
    @DisplayName("Given null user ID, when findAllDocs is called, then throw IllegalArgumentException")
    public void givenNullUserId_whenFindAllDocs_thenThrowIllegalArgumentException() {
        // Arrange
        Long userId = null;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> docService.findAllDocs(userId));
    }

    @Test
    @DisplayName("Given empty user ID, when findAllDocs is called, then throw IllegalArgumentException")
    public void givenEmptyUserId_whenFindAllDocs_thenThrowIllegalArgumentException() {
        // Arrange
        Long userId = "";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> docService.findAllDocs(userId));
    }
}