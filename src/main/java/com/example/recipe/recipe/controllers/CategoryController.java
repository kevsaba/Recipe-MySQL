package com.example.recipe.recipe.controllers;

import com.example.recipe.recipe.domains.Category;
import com.example.recipe.recipe.repositories.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Slf4j
@Controller
public class CategoryController {

    private CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @RequestMapping({"/", "", "/category"})
    public String getMyRecipes(Model model) {
        log.debug("getting categories in controller");
        Optional<Category> american = categoryRepository.findByDescription("American");
        //model.addAttribute("recipes", american);
        System.out.println(american.get().getId());
        return "index";
    }
}
