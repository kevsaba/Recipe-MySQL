package com.example.recipe.recipe.services;

import com.example.recipe.recipe.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface UomService {
    Set<UnitOfMeasureCommand> getUoms();
}
