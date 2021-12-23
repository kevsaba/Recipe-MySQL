package com.example.recipe.recipe.controllers;

import com.example.recipe.recipe.commands.IngredientCommand;
import com.example.recipe.recipe.commands.UnitOfMeasureCommand;
import com.example.recipe.recipe.services.IngredientService;
import com.example.recipe.recipe.services.RecipeService;
import com.example.recipe.recipe.services.UomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class IngredientsController {
    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UomService uomService;

    public IngredientsController(RecipeService recipeService, IngredientService ingredientService, UomService uomService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.uomService = uomService;
    }

    @GetMapping("/recipe/{recipeId}/ingredients")
    public String listRecipeIngredients(@PathVariable String recipeId, Model model) {
        log.debug("list ingredients for recipe");
        model.addAttribute("recipe", recipeService.findCommandById(Long.parseLong(recipeId)));
        return "recipe/ingredient/list";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/show")
    public String showById(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
        log.debug("show ingredient");
        model.addAttribute("ingredient", ingredientService.findIngredientByIdAndRecipeId(Long.parseLong(ingredientId), Long.parseLong(recipeId)));
        return "recipe/ingredient/show";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
        model.addAttribute("ingredient", ingredientService.findIngredientByIdAndRecipeId(Long.parseLong(ingredientId), Long.parseLong(recipeId)));
        model.addAttribute("uomList", uomService.getUoms());
        return "recipe/ingredient/ingredientform";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/new")
    public String createNewIngredient(@PathVariable String recipeId, Model model) {
        var foundRecipe = recipeService.findCommandById(Long.parseLong(recipeId));
        if (foundRecipe == null) {
            throw new RuntimeException("recipe not found");
        }
        var ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(foundRecipe.getId());
        ingredientCommand.setUom(new UnitOfMeasureCommand());
        model.addAttribute("ingredient", ingredientCommand);
        model.addAttribute("uomList", uomService.getUoms());
        return "recipe/ingredient/ingredientform";
    }

    @PostMapping("/recipe/{recipeId}/ingredient")
    public String saveOrUpdateIngredient(@ModelAttribute IngredientCommand command) {
        var savedIngredient = ingredientService.saveIngredientCommand(command);
        return "redirect:/recipe/" + savedIngredient.getRecipeId() + "/ingredient/" + savedIngredient.getId() + "/show";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/delete")
    public String deleteIngredient(@PathVariable String recipeId, @PathVariable String ingredientId) {
        ingredientService.deleteById(Long.parseLong(recipeId),Long.parseLong(ingredientId));
        return "redirect:/recipe/" + recipeId + "/ingredients";
    }
}
