package com.example.recipe.recipe.controllers;

import com.example.recipe.recipe.commands.IngredientCommand;
import com.example.recipe.recipe.commands.RecipeCommand;
import com.example.recipe.recipe.services.IngredientService;
import com.example.recipe.recipe.services.RecipeService;
import com.example.recipe.recipe.services.UomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class IngredientsControllerTest {

    @Mock
    RecipeService recipeService;
    @Mock
    IngredientService ingredientService;
    @Mock
    private UomService uomService;
    IngredientsController ingredientsController;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ingredientsController = new IngredientsController(recipeService, ingredientService, uomService);
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientsController).build();
    }

    @Test
    void listRecipeIngredients() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"));
    }

    @Test
    void showById() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/ingredient/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/show"));
    }

    @Test
    void getUpdateForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/ingredient/1/update"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("uomList"))
                //.andExpect(MockMvcResultMatchers.model().attributeExists("ingredient"))
                .andExpect(view().name("recipe/ingredient/ingredientform"));
    }

    @Test
    void getNewForm() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);
        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/ingredient/new"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("uomList"))
                //.andExpect(MockMvcResultMatchers.model().attributeExists("ingredient"))
                .andExpect(view().name("recipe/ingredient/ingredientform"));
    }

    @Test
    void postUpdateForm() throws Exception {
        //given
        var command = new IngredientCommand();
        command.setId(3l);
        command.setRecipeId(2L);
        when(ingredientService.saveIngredientCommand(any())).thenReturn(command);
        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/recipe/1/ingredient")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id","")
                        .param("description","some desc"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/ingredient/3/show"));
    }

    @Test
    void deleteIngredient() throws Exception {
        //given
        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/ingredient/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/1/ingredients"));
        //then
    }
}