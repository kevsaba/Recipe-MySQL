package com.example.recipe.recipe.services.impl;

import com.example.recipe.recipe.coverters.UnitOfMeasureToUnitOfMeasureCommand;
import com.example.recipe.recipe.domains.UnitOfMeasure;
import com.example.recipe.recipe.repositories.UnitOfMeasureRepository;
import com.example.recipe.recipe.services.UomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UomServiceImplTest {

    UomService uomService;
    @Mock
    UnitOfMeasureRepository repository;
    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        uomService = new UomServiceImpl(repository, unitOfMeasureToUnitOfMeasureCommand, null);
    }

    @Test
    void getUoms() {
        //given
        var uom1 = new UnitOfMeasure();
        uom1.setUom("1 uom");
        uom1.setId(1L);
        var uom2 = new UnitOfMeasure();
        uom2.setUom("2 uom");
        uom2.setId(2L);
        var set = new HashSet<UnitOfMeasure>();
        set.add(uom1);
        set.add(uom2);
        when(repository.findAll()).thenReturn(set);
        //when
        var ret = uomService.getUoms();
        assertEquals(2, ret.size());

    }
}