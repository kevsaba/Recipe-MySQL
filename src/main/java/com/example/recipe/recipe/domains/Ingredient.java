package com.example.recipe.recipe.domains;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
//@EqualsAndHashCode(exclude = {"recipe"})
@Entity
public class Ingredient extends BaseEntity {

    private String description;
    private BigDecimal amount;

    @OneToOne(fetch = FetchType.EAGER)
    private UnitOfMeasure unitOfMeasure;
    @ManyToOne
    private Recipe recipe;

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure unitOfMeasure) {
        this.description = description;
        this.amount = amount;
        this.unitOfMeasure = unitOfMeasure;
    }

    public Ingredient() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Ingredient that = (Ingredient) o;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        return unitOfMeasure != null ? unitOfMeasure.equals(that.unitOfMeasure) : that.unitOfMeasure == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (unitOfMeasure != null ? unitOfMeasure.hashCode() : 0);
        return result;
    }
}
