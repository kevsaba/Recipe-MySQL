package com.example.recipe.recipe.services;

import com.example.recipe.recipe.commands.IngredientCommand;

public interface IngredientService {

    IngredientCommand findIngredientByIdAndRecipeId(Long ingredientId, Long recipeId);

    IngredientCommand saveIngredientCommand(IngredientCommand command);

    void deleteById(long recipeId, long parseLong);
}
