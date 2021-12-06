package com.example.recipe.recipe.services.impl;

import com.example.recipe.recipe.model.Recipe;
import com.example.recipe.recipe.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;
    @Mock
    RecipeRepository recipeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository);
    }

    @Test
    void getRecipes() {
        //given
        final var recipe = new Recipe();
        final var list = new HashSet<Recipe>();
        list.add(recipe);
        when(recipeRepository.findAll()).thenReturn(list);
        //when
        final var recipes = recipeService.getRecipes();
        //then
        assertEquals(recipes.size(), 1);
        verify(recipeRepository, times(1)).findAll();

    }
}