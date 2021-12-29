package com.example.recipe.recipe.controllers;

import com.example.recipe.recipe.commands.RecipeCommand;
import com.example.recipe.recipe.domains.Recipe;
import com.example.recipe.recipe.exceptions.NotFoundException;
import com.example.recipe.recipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

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
    public String showById(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", recipeService.findById(id));
        return "recipe/show";
    }

    @GetMapping("recipe/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new Recipe());
        return "recipe/recipeform";
    }

    @PostMapping("/recipe")
    public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand command, BindingResult result) { //modelatribute annotation to tell spring to bind the form post parameters to the RecipeCommand object automatically by the naming conventions utilize on the form
        if (result.hasErrors()) {
            result.getAllErrors().forEach(e -> log.error(e.toString()));
            return "recipe/recipeform";
        }
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);
        return "redirect:/recipe/" + savedCommand.getId() + "/show";
    }

    @GetMapping("/recipe/{id}/update")
    public String updateRecipe(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(id));
        return "recipe/recipeform";
    }

    @GetMapping("/recipe/{id}/delete")
    public String deleteRecipe(@PathVariable Long id) {
        recipeService.deleteById(id);
        return "redirect:/recipes";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception ex) {
        log.error("Handling not found exception");
        log.error(ex.getMessage());
        ModelAndView view = new ModelAndView("404error");
        view.addObject("exception", ex);
        return view;
    }
}
