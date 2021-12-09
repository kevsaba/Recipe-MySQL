package com.example.recipe.recipe.repositories;

import com.example.recipe.recipe.domains.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
