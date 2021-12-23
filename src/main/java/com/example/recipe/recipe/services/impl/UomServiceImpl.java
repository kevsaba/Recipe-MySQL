package com.example.recipe.recipe.services.impl;

import com.example.recipe.recipe.commands.UnitOfMeasureCommand;
import com.example.recipe.recipe.coverters.UnitOfMeasureCommandToUnitOfMeasure;
import com.example.recipe.recipe.coverters.UnitOfMeasureToUnitOfMeasureCommand;
import com.example.recipe.recipe.repositories.UnitOfMeasureRepository;
import com.example.recipe.recipe.services.UomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class UomServiceImpl implements UomService {

    private final UnitOfMeasureRepository repository;
    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;
    private final UnitOfMeasureCommandToUnitOfMeasure unitOfMeasure;

    public UomServiceImpl(UnitOfMeasureRepository repository, UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand, UnitOfMeasureCommandToUnitOfMeasure unitOfMeasure) {
        this.repository = repository;
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
        this.unitOfMeasure = unitOfMeasure;
    }

    @Override
    public Set<UnitOfMeasureCommand> getUoms() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(unitOfMeasureToUnitOfMeasureCommand::convert)
                .collect(Collectors.toSet());
    }
}
