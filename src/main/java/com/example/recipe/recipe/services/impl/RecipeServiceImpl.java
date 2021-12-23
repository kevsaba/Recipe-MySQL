package com.example.recipe.recipe.services.impl;

import com.example.recipe.recipe.commands.RecipeCommand;
import com.example.recipe.recipe.coverters.RecipeCommandToRecipe;
import com.example.recipe.recipe.coverters.RecipeToRecipeCommand;
import com.example.recipe.recipe.domains.Recipe;
import com.example.recipe.recipe.repositories.RecipeRepository;
import com.example.recipe.recipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Slf4j //its a logger so I can do log();
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("Im in the service getting the recipes");
        final var recipes = new HashSet<Recipe>();
        recipeRepository.findAll().iterator().forEachRemaining(recipes::add);
        return recipes;
    }

    @Override
    public Recipe findById(long id) {
        log.debug("Im in the service getting the recipes find by id");
        final var ret = recipeRepository.findById(id);
        if (ret.isEmpty()) {
            throw new RuntimeException("Recipe not found");
        }
        return ret.get();
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand recipe) {
        final var saved = recipeRepository.save(recipeCommandToRecipe.convert(recipe));
        log.debug("recipe comand converted into recipe and saved");
        return recipeToRecipeCommand.convert(saved);
    }

    @Override
    @Transactional
    public RecipeCommand findCommandById(Long id) {
        log.debug("finding by id and converting to command");
        return recipeToRecipeCommand.convert(findById(id));
    }

    @Override
    public void deleteById(long id) {
        log.debug("deleting recipe with id" + id);
        recipeRepository.deleteById(id);
    }
}
