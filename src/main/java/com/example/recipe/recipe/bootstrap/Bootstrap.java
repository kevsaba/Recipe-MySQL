package com.example.recipe.recipe.bootstrap;

import com.example.recipe.recipe.enums.Dificulty;
import com.example.recipe.recipe.domains.Ingredient;
import com.example.recipe.recipe.domains.Notes;
import com.example.recipe.recipe.domains.Recipe;
import com.example.recipe.recipe.repositories.CategoryRepository;
import com.example.recipe.recipe.repositories.RecipeRepository;
import com.example.recipe.recipe.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Slf4j
@Component
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private RecipeRepository recipeRepository;
    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;

    public Bootstrap(RecipeRepository recipeRepository, CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    @Transactional //we tell the spring framework to wrap this method in the same transaction so everything happens in the same transactional context
    // the many to many that are lazy so that transaction will happen later once is invoked causing an exception due to happening in an old context
    public void onApplicationEvent(ContextRefreshedEvent event) {
        recipeRepository.saveAll(getRecipes());
        log.debug("saving all bootstrap data");
    }

    private List<Recipe> getRecipes() {
        final List<Recipe> recipes = new ArrayList<>();
        final var each = unitOfMeasureRepository.findByUom("Each");
        final var uomEach = each.get();
        if (isNull(uomEach)) {
            throw new RuntimeException("UOM doesn't exist");
        }
        final var tablespoon = unitOfMeasureRepository.findByUom("Tablespoon");
        final var uomTablespoon = tablespoon.get();
        if (isNull(uomTablespoon)) {
            throw new RuntimeException("UOM doesn't exist");
        }
        final var teaspoon = unitOfMeasureRepository.findByUom("Teaspoon");
        final var uomTeaspoon = teaspoon.get();
        if (isNull(uomTeaspoon)) {
            throw new RuntimeException("UOM doesn't exist");
        }
        final var dash = unitOfMeasureRepository.findByUom("Dash");
        final var uomDash = dash.get();
        if (isNull(uomDash)) {
            throw new RuntimeException("UOM doesn't exist");
        }
        final var pinch = unitOfMeasureRepository.findByUom("Pinch");
        final var uomPinch = pinch.get();
        if (isNull(uomPinch)) {
            throw new RuntimeException("UOM doesn't exist");
        }
        final var cup = unitOfMeasureRepository.findByUom("Cup");
        final var uomCup = cup.get();
        if (isNull(uomCup)) {
            throw new RuntimeException("UOM doesn't exist");
        }

        final var american = categoryRepository.findByDescription("American");
        final var categoryAmerican = american.get();
        if (isNull(categoryAmerican)) {
            throw new RuntimeException("Category doesn't exist");
        }

        final var mexican = categoryRepository.findByDescription("Mexican");
        final var categoryMexican = mexican.get();
        if (isNull(categoryMexican)) {
            throw new RuntimeException("Category doesn't exist");
        }

        final var guacamoleRecipe = new Recipe();
        guacamoleRecipe.setDescription("Perfect Guacamole");
        guacamoleRecipe.setCookTime(0);
        guacamoleRecipe.setPrepTime(10);
        guacamoleRecipe.setDificulty(Dificulty.EASY);
        guacamoleRecipe.setDirections("1. Cut the avocado:\n" +
                "Cut the avocados in half. Remove the pit. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl.," +
                "2. Mash the avocado flesh:\n" +
                "Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)" +
                "3. Add remaining ingredients to taste:\n" +
                "Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "\n" +
                "Add the chopped onion, cilantro, black pepper, and chilis. Chili peppers vary individually in their spiciness. So, start with a half of one chili pepper and add more to the guacamole to your desired degree of heat.\n" +
                "\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste." +
                "4. Serve immediately:\n" +
                "If making a few hours ahead, place plastic wrap on the surface of the guacamole and press down to cover it to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.)\n" +
                "\n" +
                "Garnish with slices of red radish or jigama strips. Serve with your choice of store-bought tortilla chips or make your own homemade tortilla chips.\n" +
                "\n" +
                "Refrigerate leftover guacamole up to 3 days.\n" +
                "\n" +
                "Note: Chilling tomatoes hurts their flavor. So, if you want to add chopped tomato to your guacamole, add it just before serving.");
        final var guacNotes = new Notes();
        guacNotes.setRecipeNotes("Be careful handling chilis! If using, it's best to wear food-safe gloves. If no gloves are available, wash your hands thoroughly after handling, and do not touch your eyes or the area near your eyes for several hours afterwards.");
        guacNotes.setRecipe(guacamoleRecipe);
        guacamoleRecipe.setNotes(guacNotes);
        guacamoleRecipe.addIngredient(new Ingredient("ripe avocados", new BigDecimal(2), uomEach));
        guacamoleRecipe.addIngredient(new Ingredient("salt", new BigDecimal("0.25"), uomTeaspoon));
        guacamoleRecipe.addIngredient(new Ingredient("fresh lime or lemon juice", new BigDecimal(1), uomTablespoon));
        guacamoleRecipe.addIngredient(new Ingredient("minced red onion or thinly sliced green onion", new BigDecimal(3), uomTablespoon));
        guacamoleRecipe.addIngredient(new Ingredient("serrano (or jalapeño) chilis, stems and seeds removed, minced", new BigDecimal(2), uomEach));
        guacamoleRecipe.addIngredient(new Ingredient("cilantro (leaves and tender stems), finely chopped", new BigDecimal(2), uomTablespoon));
        guacamoleRecipe.addIngredient(new Ingredient("freshly ground black pepper", new BigDecimal(1), uomPinch));

        guacamoleRecipe.getCategories().add(categoryAmerican);
        guacamoleRecipe.getCategories().add(categoryMexican);
        recipes.add(guacamoleRecipe);
        return recipes;
    }

}
