package com.example.recipe.recipe.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CategoryTest {

    Category category;

    @BeforeEach
    public void setUp() {
        category = new Category();
    }

    @Test
    void getId() {
        Long id = 4l;
        category.setId(id);
        Assertions.assertEquals(id, category.getId());
    }

    @Test
    void getIdNull() {
        Assertions.assertEquals(null, category.getId());
    }

    @Test
    void getDescription() {
    }

    @Test
    void getRecipes() {
    }
}