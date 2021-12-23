package com.example.recipe.recipe.services.impl;

import com.example.recipe.recipe.commands.IngredientCommand;
import com.example.recipe.recipe.coverters.IngredientCommandToIngredient;
import com.example.recipe.recipe.coverters.IngredientToIngredientCommand;
import com.example.recipe.recipe.coverters.UnitOfMeasureCommandToUnitOfMeasure;
import com.example.recipe.recipe.coverters.UnitOfMeasureToUnitOfMeasureCommand;
import com.example.recipe.recipe.domains.Ingredient;
import com.example.recipe.recipe.domains.Recipe;
import com.example.recipe.recipe.repositories.RecipeRepository;
import com.example.recipe.recipe.repositories.UnitOfMeasureRepository;
import com.example.recipe.recipe.services.IngredientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class IngredientServiceImplTest {
    IngredientService ingredientService;
    @Mock
    RecipeRepository recipeRepository;
    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    IngredientCommandToIngredient ingredientCommandToIngredient;

    IngredientToIngredientCommand ingredientToIngredientCommand;

    public IngredientServiceImplTest() {
        ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ingredientService = new IngredientServiceImpl(recipeRepository, unitOfMeasureRepository, ingredientCommandToIngredient, ingredientToIngredientCommand);
    }

    @Test
    void findIngredientByIdAndRecipeId() {
        //given
        var ingredient = new Ingredient();
        ingredient.setId(1L);
        var recipe = new Recipe();
        recipe.setId(2L);
        recipe.addIngredient(ingredient);
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        //when
        var ret = ingredientService.findIngredientByIdAndRecipeId(1L, 2L);
        //then
        assertEquals(1L, ret.getId());
        assertEquals(2L, ret.getRecipeId());
    }

    @Test
    void saveIngredientCommand() {
        //given
        var command = new IngredientCommand();
        command.setId(5L);
        command.setRecipeId(2L);
        var savedRecipe = new Recipe();
        savedRecipe.setId(2L);
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId(5L);
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(new Recipe()));
        when(recipeRepository.save(any())).thenReturn(savedRecipe);

        //when
        var ret = ingredientService.saveIngredientCommand(command);
        //then
        assertEquals(5L, ret.getId());
    }

    @Test
    void deleteIngredient() {
        //given
        var ingredient = new Ingredient();
        ingredient.setId(2L);
        var recipe = new Recipe();
        recipe.setId(1L);
        recipe.addIngredient(ingredient);
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        //when
        ingredientService.deleteById(1L, 2L);
        //then
        verify(recipeRepository,times(1)).findById(anyLong());
        verify(recipeRepository,times(1)).save(any(Recipe.class));
    }
}