package com.example.recipe.recipe.coverters;

import com.example.recipe.recipe.commands.UnitOfMeasureCommand;
import com.example.recipe.recipe.domains.UnitOfMeasure;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class UnitOfMeasureCommandToUnitOfMeasure implements Converter<UnitOfMeasureCommand, UnitOfMeasure> {


    @Override
    public UnitOfMeasure convert(UnitOfMeasureCommand source) {
        if (isNull(source)) {
            return null;
        }
        final var uom = new UnitOfMeasure();
        uom.setId(source.getId());
        uom.setUom(source.getUom());
        return uom;
    }
}
