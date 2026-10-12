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

import com.demo.app.model.Document;
import com.demo.app.repository.DocumentRepository;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class DocServiceTest {

    @InjectMocks
    private DocService docService;

    @Mock
    private DocumentRepository documentRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("GivenExistingId_WhenFindAllDocs_thenReturnDocs")
    public void testFindAllDocs_ExistingId() {
        // Arrange
        Long existingId = 1L;
        List<Document> expectedDocs = new ArrayList<>();
        expectedDocs.add(new Document());
        when(documentRepository.findAllById(existingId)).thenReturn(expectedDocs);

        // Act
        List<Document> result = docService.findAllDocs(existingId);

        // Assert
        assertEquals(expectedDocs, result);
        verify(documentRepository, times(1)).findAllById(existingId);
    }

    @Test
    @DisplayName("GivenNonExistingId_WhenFindAllDocs_thenReturnEmptyList")
    public void testFindAllDocs_NonExistingId() {
        // Arrange
        Long nonExistingId = 2L;
        when(documentRepository.findAllById(nonExistingId)).thenReturn(new ArrayList<>());

        // Act
        List<Document> result = docService.findAllDocs(nonExistingId);

        // Assert
        assertTrue(result.isEmpty());
        verify(documentRepository, times(1)).findAllById(nonExistingId);
    }

    @Test
    @DisplayName("GivenNullId_WhenFindAllDocs_thenReturnEmptyList")
    public void testFindAllDocs_NullId() {
        // Arrange
        Long nullId = null;
        when(documentRepository.findAllById(nullId)).thenReturn(new ArrayList<>());

        // Act
        List<Document> result = docService.findAllDocs(nullId);

        // Assert
        assertTrue(result.isEmpty());
        verify(documentRepository, times(1)).findAllById(nullId);
    }

    // Additional tests for other public business methods and uncovered branches can be added here
}