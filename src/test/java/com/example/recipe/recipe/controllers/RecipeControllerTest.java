package com.example.recipe.recipe.controllers;

import com.example.recipe.recipe.commands.RecipeCommand;
import com.example.recipe.recipe.domains.Recipe;
import com.example.recipe.recipe.exceptions.NotFoundException;
import com.example.recipe.recipe.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class RecipeControllerTest {
    @Mock
    RecipeService recipeService;
    @Mock
    Model model;
    RecipeController recipeController;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        recipeController = new RecipeController(recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController).setControllerAdvice(ControllerExceptionHandler.class).build();
    }

    //this is to test controllers without needing to bring the spring context which is heavy
    @Test
    void testMockMvc() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).setControllerAdvice(ControllerExceptionHandler.class).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(status().isOk()).andExpect(view().name("index"));
    }

    @Test
    void getMyRecipes() {
        //given
        final var recipesToCapture = new HashSet<Recipe>();
        Recipe e = new Recipe();
        e.setId(1L);
        recipesToCapture.add(e);
        recipesToCapture.add(new Recipe());
        when(recipeService.getRecipes()).thenReturn(recipesToCapture);
        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);
        //when
        final var recipes = recipeController.getMyRecipes(model);

        //then
        assertEquals("index", recipes);
        verify(recipeService, times(1)).getRecipes();
        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
        Set<Recipe> setInController = argumentCaptor.getValue();
        assertEquals(2, setInController.size());
    }

    @Test
    void showById() throws Exception {
        //given
        var recipe = new Recipe();
        recipe.setId(1L);
        when(recipeService.findById(anyLong())).thenReturn(recipe);
        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));

    }

    @Test
    void newRecipe() throws Exception {
        //given
        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"))
                .andExpect(view().name("recipe/recipeform"));
    }

    @Test
    void saveOrUpdate() throws Exception {
        //given
        var recipe = new RecipeCommand();
        recipe.setId(2L);
        when(recipeService.saveRecipeCommand(any())).thenReturn(recipe);
        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id","")
                        .param("description","setting up the description in the test")
                        .param("directions","setting up the direction in the test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/show"));
    }

    @Test
    void saveOrUpdateHasErrors() throws Exception {
        //given
        var recipe = new RecipeCommand();
        recipe.setId(2L);
        when(recipeService.saveRecipeCommand(any())).thenReturn(recipe);
        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/recipe")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id","")
                        .param("description","setting up the description in the test"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"));
    }

    @Test
    void deleteRecipe() throws Exception {
        //given
        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipes"));
        verify(recipeService,times(1)).deleteById(anyLong());
    }

    @Test
    void notFoundRecipeException() throws Exception {
        when(recipeService.findById(anyLong())).thenThrow(NotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/show"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404error"));
    }

    @Test
    void BadRequestException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/asd/show"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"));
    }
}