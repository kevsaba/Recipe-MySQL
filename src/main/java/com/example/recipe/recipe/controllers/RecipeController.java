package com.example.recipe.recipe.controllers;

import com.example.recipe.recipe.commands.RecipeCommand;
import com.example.recipe.recipe.domains.Recipe;
import com.example.recipe.recipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping({"/", "/", "/recipes"})
    public String getMyRecipes(Model model) {
        log.debug("recipe controller called");
        model.addAttribute("recipes", recipeService.getRecipes());
        return "index";
    }

    @GetMapping({"/recipe/{id}/show"})
    public String showById(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findById(Long.parseLong(id)));
        return "recipe/show";
    }

    @GetMapping("recipe/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new Recipe());
        return "recipe/recipeform";
    }

    @PostMapping("/recipe")
    public String saveOrUpdate(@ModelAttribute RecipeCommand command) { //modelatribute annotation to tell spring to bind the form post parameters to the RecipeCommand object automatically by the naming conventions utilize on the form
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);
        return "redirect:/recipe/" + savedCommand.getId() + "/show";
    }

    @GetMapping("/recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(Long.parseLong(id)));
        return "recipe/recipeform";
    }

    @GetMapping("/recipe/{id}/delete")
    public String deleteRecipe(@PathVariable String id) {
        recipeService.deleteById(Long.parseLong(id));
        return "redirect:/recipes";
    }
}
