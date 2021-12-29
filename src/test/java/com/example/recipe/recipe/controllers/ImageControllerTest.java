package com.example.recipe.recipe.controllers;

import com.example.recipe.recipe.commands.RecipeCommand;
import com.example.recipe.recipe.services.ImageService;
import com.example.recipe.recipe.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ImageControllerTest {

    @Mock
    ImageService imageService;
    @Mock
    RecipeService recipeService;

    ImageController controller;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new ImageController(imageService, recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(ControllerExceptionHandler.class).build();
    }

    @Test
    void getUploadRecipeImageForm() throws Exception {
        //given
        var command = new RecipeCommand();
        command.setId(1L);
        when(recipeService.findCommandById(anyLong())).thenReturn(command);
        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/image"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"));
        //then
        verify(recipeService, times(1)).findCommandById(anyLong());
    }

    @Test
    void uploadRecipeImage() throws Exception {
        //given
        var file = new MockMultipartFile("imagefile", "testing.txt", "text/plain", "Spring Framework".getBytes());
        //when
        mockMvc.perform(multipart("/recipe/1/image").file(file))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("location", "/recipe/1/show"));
        //then
        verify(imageService, times(1)).saveImageFile(anyLong(), any());
    }

    @Test
    void getImageToRender() throws Exception {
        //given
        var command =  new RecipeCommand();
        command.setId(1L);
        String s = "faking an image";
        Byte[] byteBoxed = new Byte[s.getBytes().length];
        int i = 0;
        for (var b :s.getBytes()) {
            byteBoxed[i++] = b;
        }
        command.setImage(byteBoxed);
        when(recipeService.findCommandById(anyLong())).thenReturn(command);
        //when
        var response = mockMvc.perform(get("/recipe/1/recipeimage"))
                .andExpect(status().isOk())
                .andReturn().getResponse();
        //then
        var byteArr = response.getContentAsByteArray();
        assertEquals(s.getBytes().length,byteArr.length);

    }

    @Test
    void BadRequestException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/asd/recipeimage"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"));
    }
}