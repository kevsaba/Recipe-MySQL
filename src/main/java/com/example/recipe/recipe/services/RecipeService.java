package com.example.recipe.recipe.services;

import com.example.recipe.recipe.commands.RecipeCommand;
import com.example.recipe.recipe.domains.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();

    Recipe findById(long id);

    RecipeCommand saveRecipeCommand(RecipeCommand recipe);

    RecipeCommand findCommandById(Long id);

    void deleteById(long parseLong);
}
