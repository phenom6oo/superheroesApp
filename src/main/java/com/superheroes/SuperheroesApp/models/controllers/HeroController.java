package com.superheroes.SuperheroesApp.models.controllers;
import com.superheroes.SuperheroesApp.models.exceptions.NotFoundException;
import com.superheroes.SuperheroesApp.models.models.HeroEntity;
import com.superheroes.SuperheroesApp.models.services.HeroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@RequestMapping("/api/heroes")public class HeroController {

    private static final Logger log = LoggerFactory.getLogger(HeroController.class);

    @Autowired
    private HeroService heroService;

    @GetMapping
    public List<HeroEntity> getAllHeroes() {
        log.info("Getting all heroes");
        List<HeroEntity> heroes = heroService.getAllHeroes();
        log.info("Returning {} heroes", heroes.size());
        return heroes;
    }

    @GetMapping("/{id}")
    public Optional<HeroEntity> getHeroById(@PathVariable Long id) {
        log.info("Getting hero with ID: {}", id);
        return heroService.getHeroById(id);
    }

    @GetMapping("/search")
    public List<HeroEntity> getHeroesByName(@RequestParam String name) {
        log.info("Searching heroes by name: {}", name);
        List<HeroEntity> heroes = heroService.getHeroesByName(name);
        log.info("Returning {} heroes", heroes.size());
        return heroes;
    }

    @PostMapping
    public ResponseEntity<HeroEntity> createHero(@RequestBody HeroEntity hero) {
        log.info("Creating hero: {}", hero);
        HeroEntity createdHero = heroService.createHero(hero);
        log.info("Hero created: {}", createdHero);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdHero);

    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateHero(@PathVariable Long id, @RequestBody HeroEntity hero) {
        try {
            log.info("Updating hero with ID: {}", id);
            HeroEntity updatedHero = heroService.updateHero(id, hero);
            log.info("Hero updated: {}", updatedHero);
            return ResponseEntity.ok("Hero updated successfully");
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHero(@PathVariable Long id) {
        log.info("Deleting hero with ID: {}", id);
        try {
            heroService.deleteHero(id);
            log.info("Hero deleted with ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException e) {
            log.error("Error deleting hero with ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}