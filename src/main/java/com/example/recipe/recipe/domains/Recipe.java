package com.example.recipe.recipe.domains;

import com.example.recipe.recipe.enums.Dificulty;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.isNull;

@Data
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    @Lob
    private String directions;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Ingredient> ingredients = new HashSet<>();
    @Lob //this will be a BLOB
    private Byte[] image;
    @OneToOne(cascade = CascadeType.ALL)
    private Notes notes;
    @Enumerated(value = EnumType.STRING)
    // its what is persisted on the database, the order or the string literal name of it
    private Dificulty dificulty;
    @ManyToMany
    //if the join table is not specified then it will not create a table with both tables id's but it will create 2 tables with back and foward ids
    //so to do this correctly when many to many at least you should set the @JoinTable to specify how this new table should be set
    @JoinTable(name = "recipe_category", joinColumns = @JoinColumn(name = "recipe_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    public void setNotes(Notes notes) {
        if (!isNull(notes)) {
            this.notes = notes;
            this.notes.setRecipe(this);
        }
    }

    public void addIngredient(Ingredient ingredient) {
        ingredient.setRecipe(this);
        this.ingredients.add(ingredient);
    }

}
