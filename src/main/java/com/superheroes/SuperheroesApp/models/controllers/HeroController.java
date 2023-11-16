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
@RestController
@RequestMapping("/api/heroes")public class HeroController {

    @Autowired
    private HeroService heroService;

    @GetMapping
    public List<HeroEntity> getAllHeroes() {
        return heroService.getAllHeroes();
    }

    @GetMapping("/{id}")
    public Optional<HeroEntity> getHeroById(@PathVariable Long id) {
        return heroService.getHeroById(id);
    }

    @GetMapping("/search")
    public List<HeroEntity> getHeroesByName(@RequestParam String name) {
        return heroService.getHeroesByName(name);
    }

    @PostMapping
    public HeroEntity createHero(@RequestBody HeroEntity hero) {
        return heroService.createHero(hero);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateHero(@PathVariable Long id, @RequestBody HeroEntity hero) {
        try {
            HeroEntity updatedHero = heroService.updateHero(id, hero);
            return ResponseEntity.ok("Hero updated successfully");
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHero(@PathVariable Long id) {
        try {
            heroService.deleteHero(id);
            return ResponseEntity.ok("Hero deleted successfully");
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + e.getMessage());
        }
    }
}