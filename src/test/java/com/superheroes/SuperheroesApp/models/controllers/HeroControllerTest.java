package com.superheroes.SuperheroesApp.models.controllers;

import com.superheroes.SuperheroesApp.models.models.HeroEntity;
import com.superheroes.SuperheroesApp.models.services.HeroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class HeroControllerTest {

    @Mock
    private HeroService heroService;

    @InjectMocks
    private HeroController heroController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllHeroes() {
        when(heroService.getAllHeroes()).thenReturn(Arrays.asList(new HeroEntity(), new HeroEntity()));
        List<HeroEntity> heroes = heroController.getAllHeroes();
        assertEquals(2, heroes.size());
    }

    @Test
    void testGetHeroById() {
        when(heroService.getHeroById(1L)).thenReturn(Optional.of(new HeroEntity(1L, "Spiderman")));
        Optional<HeroEntity> hero = heroController.getHeroById(1L);
        assertEquals(1L, hero.get().getId());
    }

    @Test
    void testGetHeroesByName() {
        when(heroService.getHeroesByName("Ironman")).thenReturn(Arrays.asList(new HeroEntity(), new HeroEntity()));
        List<HeroEntity> heroes = heroController.getHeroesByName("Ironman");
        assertEquals(2, heroes.size());
    }

    @Test
    void testCreateHero() {
        HeroEntity heroToCreate = new HeroEntity();
        when(heroService.createHero(heroToCreate)).thenReturn(heroToCreate);
        HeroEntity createdHero = heroController.createHero(heroToCreate);
        assertEquals(heroToCreate, createdHero);
    }

    @Test
    void testUpdateHero() {
        HeroEntity existingHero = new HeroEntity();
        when(heroService.updateHero(1L, existingHero)).thenReturn(existingHero);
        HeroEntity updatedHero = heroController.updateHero(1L, existingHero);
        assertEquals(existingHero, updatedHero);
    }

    @Test
    void testDeleteHero() {
        heroController.deleteHero(1L);
        verify(heroService, times(1)).deleteHero(1L);
    }
}
