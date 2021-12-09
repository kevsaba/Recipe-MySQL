package com.example.recipe.recipe.domains;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
//@EqualsAndHashCode(exclude = {"recipes"})
@Entity
public class Category extends BaseEntity{

    private String description;
    @ManyToMany(mappedBy = "categories")
    private Set<Recipe> recipes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Category category = (Category) o;
        if (id != null ? !id.equals(category.id) : category.id != null) return false;
        return description != null ? description.equals(category.description) : category.description == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
