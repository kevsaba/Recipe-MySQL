package com.example.recipe.recipe.services.impl;

import com.example.recipe.recipe.commands.IngredientCommand;
import com.example.recipe.recipe.coverters.IngredientCommandToIngredient;
import com.example.recipe.recipe.coverters.IngredientToIngredientCommand;
import com.example.recipe.recipe.domains.Ingredient;
import com.example.recipe.recipe.repositories.RecipeRepository;
import com.example.recipe.recipe.repositories.UnitOfMeasureRepository;
import com.example.recipe.recipe.services.IngredientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {


    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;

    public IngredientServiceImpl(RecipeRepository recipeRepository,
                                 UnitOfMeasureRepository unitOfMeasureRepository, IngredientCommandToIngredient ingredientCommandToIngredient,
                                 IngredientToIngredientCommand ingredientToIngredientCommand) {
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
    }

    @Override
    public IngredientCommand findIngredientByIdAndRecipeId(Long ingredientId, Long recipeId) {
        log.debug("find recipe by id and ingredients since I dont have a repository created");
        final var recipeOptional = recipeRepository.findById(recipeId);
        if (recipeOptional.isEmpty()) {
            throw new RuntimeException("Recipe doesnt exist");
        }

        return recipeOptional.get().getIngredients()
                .stream()
                .filter(i -> i.getId().equals(ingredientId))
                .map(ingredientToIngredientCommand::convert)
                .findFirst()
                .orElse(null);
    }

    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {
        var recipe = recipeRepository.findById(command.getRecipeId());
        if (recipe.isEmpty()) {
            throw new RuntimeException("recipe not found");
        }
        var ingredientFound = recipe.get().getIngredients().stream().filter(i -> i.getId().equals(command.getId())).findFirst();
        if (ingredientFound.isPresent()) {
            var ingredient = ingredientFound.get();
            ingredient.setAmount(command.getAmount());
            ingredient.setDescription(command.getDescription());
            ingredient.setUnitOfMeasure(unitOfMeasureRepository.findById(command.getUom().getId()).orElseThrow(() -> new RuntimeException("uom not found")));
        } else {
            recipe.get().addIngredient(ingredientCommandToIngredient.convert(command));
        }
        var savedRecipe = recipeRepository.save(recipe.get());

        Ingredient ingredient;
        if (command.getId() != null) {
            ingredient = savedRecipe.getIngredients()
                    .stream()
                    .filter(i -> i.getId().equals(command.getId()))
                    .findFirst()
                    .orElse(null);
        } else {
            ingredient = savedRecipe.getIngredients()
                    .stream()
                    .filter(i -> i.getDescription().equals(command.getDescription()))
                    .filter(i -> i.getAmount().equals(command.getAmount()))
                    .filter(i -> i.getUnitOfMeasure().getId().equals(command.getUom().getId()))
                    .findFirst()
                    .orElse(null);
        }

        return ingredientToIngredientCommand.convert(ingredient);
    }

    @Override
    public void deleteById(long recipeId, long ingredientId) {
        log.debug("deleting recipe with id" + ingredientId);
        var recipe = recipeRepository.findById(recipeId);
        if (recipe.isEmpty()) {
            throw new RuntimeException("recipe id doesnt exist");
        }
        var ingredient = recipe.get().getIngredients().stream().filter(i -> i.getId().equals(ingredientId)).findFirst();
        if (ingredient.isPresent()){
            ingredient.get().setRecipe(null);
            recipe.get().getIngredients().removeIf(i -> i.getId().equals(ingredientId));
            recipeRepository.save(recipe.get());
        }
    }
}
