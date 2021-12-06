package com.example.recipe.recipe.controller;

import com.example.recipe.recipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"/", "/", "/recipes"})
    public String getMyRecipes(Model model) {
        log.debug("recipe controller called");
        model.addAttribute("recipes", recipeService.getRecipes());
        return "index";
    }
}
