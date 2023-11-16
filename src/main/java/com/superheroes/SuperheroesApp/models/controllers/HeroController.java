package com.superheroes.SuperheroesApp.models.controllers;
import com.superheroes.SuperheroesApp.models.models.HeroEntity;
import com.superheroes.SuperheroesApp.models.services.HeroService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public HeroEntity updateHero(@PathVariable Long id, @RequestBody HeroEntity hero) {
        return heroService.updateHero(id, hero);
    }

    @DeleteMapping("/{id}")
    public void deleteHero(@PathVariable Long id) {
        heroService.deleteHero(id);
    }
}