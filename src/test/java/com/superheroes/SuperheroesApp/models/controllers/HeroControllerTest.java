package com.superheroes.SuperheroesApp.models.controllers;

import com.superheroes.SuperheroesApp.models.exceptions.NotFoundException;
import com.superheroes.SuperheroesApp.models.models.HeroEntity;
import com.superheroes.SuperheroesApp.models.services.HeroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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
        assertTrue(hero.isPresent());
        assertEquals(1L, hero.get().getId());
    }

    @Test
    void testGetHeroByIdWithException() {
        when(heroService.getHeroById(1L)).thenThrow(new NotFoundException("Hero not found with ID: 1"));
        assertThrows(NotFoundException.class, () -> heroController.getHeroById(1L));
    }

    @Test
    void testGetHeroByIdWithEmptyOptional() {
        when(heroService.getHeroById(1L)).thenReturn(Optional.empty());
        Optional<HeroEntity> hero = heroController.getHeroById(1L);
        assertTrue(hero.isEmpty());
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
        ResponseEntity<HeroEntity> responseEntity = heroController.createHero(heroToCreate);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(heroToCreate, responseEntity.getBody());
    }


    @Test
    void testUpdateHero() {
        HeroEntity existingHero = new HeroEntity();
        when(heroService.updateHero(1L, existingHero)).thenReturn(existingHero);
        ResponseEntity<String> responseEntity = heroController.updateHero(1L, existingHero);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Hero updated successfully", responseEntity.getBody());
    }

    @Test
    void testUpdateHeroWithException() {
        HeroEntity existingHero = new HeroEntity();
        when(heroService.updateHero(1L, existingHero)).thenThrow(new NotFoundException("Hero not found with ID: 1"));
        ResponseEntity<String> responseEntity = heroController.updateHero(1L, existingHero);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Hero not found with ID: 1", responseEntity.getBody());
    }

    @Test
    void testDeleteHero() {
        heroController.deleteHero(1L);
        verify(heroService, times(1)).deleteHero(1L);
    }

    //TODO
    /*
    @Test
    void testDeleteHeroWithException() {
        doThrow(new NotFoundException("Hero not found with ID: 1")).when(heroService).deleteHero(1L);
        ResponseEntity<String> responseEntity = heroController.deleteHero(1L);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Hero not found with ID: 1", responseEntity.getBody());
    }
*/
}
