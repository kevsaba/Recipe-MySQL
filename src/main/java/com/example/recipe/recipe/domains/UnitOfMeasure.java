package com.example.recipe.recipe.domains;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class UnitOfMeasure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uom;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UnitOfMeasure that = (UnitOfMeasure) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return uom != null ? uom.equals(that.uom) : that.uom == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (uom != null ? uom.hashCode() : 0);
        return result;
    }
}
