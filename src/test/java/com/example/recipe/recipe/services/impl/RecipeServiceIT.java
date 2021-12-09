package com.example.recipe.recipe.services.impl;

import com.example.recipe.recipe.coverters.RecipeToRecipeCommand;
import com.example.recipe.recipe.repositories.RecipeRepository;
import com.example.recipe.recipe.services.RecipeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class) //TO BRING THE APP CONTEXT
//@DataJpaTest // to bring the embebed database and configure spring jpa for us but this is in a light way here so its not enough to find the recipeservice
@SpringBootTest //this will bring the whole context and with this it will run properly
public class RecipeServiceIT {
    public static final String NEW_DESCRIPTION = "New Description";

    @Autowired //since i have the spring app context I can do this no need to mocking
    RecipeService recipeService;
    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Test
    @Transactional //because Im accessing lazy loading entities like categories and that will be outside the current context which could break this thats why the transactional to keep everything in one whole context
    public void testSaveDescription() {
        //given
        final var all = recipeRepository.findAll();
        final var dbRecipe = all.iterator().next();
        final var command = recipeToRecipeCommand.convert(dbRecipe);
        command.setDescription(NEW_DESCRIPTION);

        //when
        final var savedRecipeCommand = recipeService.saveRecipeCommand(command);

        //then
        assertEquals(NEW_DESCRIPTION, savedRecipeCommand.getDescription());
        assertEquals(dbRecipe.getId(), savedRecipeCommand.getId());
        assertEquals(dbRecipe.getCategories().size(), savedRecipeCommand.getCategories().size());

    }
}
