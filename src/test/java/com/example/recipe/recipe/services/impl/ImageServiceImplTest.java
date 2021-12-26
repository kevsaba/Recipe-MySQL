package com.example.recipe.recipe.services.impl;

import com.example.recipe.recipe.domains.Recipe;
import com.example.recipe.recipe.repositories.RecipeRepository;
import com.example.recipe.recipe.services.ImageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ImageServiceImplTest {

    @Mock
    RecipeRepository repository;
    ImageService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new ImageServiceImpl(repository);
    }

    @Test
    void saveImageFile() throws IOException {
        //given
        var file = new MockMultipartFile("imagefile", "testing.txt", "text/plain", "Spring Framework Guru".getBytes());
        var recipe = new Recipe();
        recipe.setId(1L);
        when(repository.findById(anyLong())).thenReturn(Optional.of(recipe));

        ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);
        //when
        service.saveImageFile(1L, file);
        //then
        verify(repository, times(1)).save(argumentCaptor.capture());
        assertEquals(file.getBytes().length,argumentCaptor.getValue().getImage().length);
    }
}