package com.example.recipe.recipe.services.impl;

import com.example.recipe.recipe.coverters.RecipeCommandToRecipe;
import com.example.recipe.recipe.coverters.RecipeToRecipeCommand;
import com.example.recipe.recipe.domains.Recipe;
import com.example.recipe.recipe.exceptions.NotFoundException;
import com.example.recipe.recipe.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;
    @Mock
    RecipeRepository recipeRepository;
    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;
    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
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

    @Test
    void findById() {
        //given
        final var recipe = new Recipe();
        recipe.setId(1L);
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        //when
        final var ret = recipeService.findById(1L);
        //then
        assertEquals(ret.getId(), 1L);
        verify(recipeRepository, times(1)).findById(anyLong());
    }

    @Test
    void getRecipeFindByIdNotFound() {
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            recipeService.findById(1L);
        });

    }

}