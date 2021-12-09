package com.example.recipe.recipe.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class) //TO BRING THE APP CONTEXT
@DataJpaTest // to bring the embebed database and configure spring jpa for us
class UnitOfMeasureRepositoryIT {

    @Autowired // this is why we need the app context
    UnitOfMeasureRepository unitOfMeasureRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    // @DirtiesContext
    void findByUom() {
        final var uom = unitOfMeasureRepository.findByUom("Teaspoon");
        assertTrue(uom.isPresent());
        assertEquals("Teaspoon", uom.get().getUom());
    }

    @Test
    void findByUomCup() {
        final var uom = unitOfMeasureRepository.findByUom("Cup");
        assertTrue(uom.isPresent());
        assertEquals("Cup", uom.get().getUom());
    }
}