package com.superheroes.SuperheroesApp.models.services;

import com.superheroes.SuperheroesApp.models.models.HeroEntity;

import java.util.List;
import java.util.Optional;

public interface HeroService {
    List<HeroEntity> getAllHeroes();
    Optional<HeroEntity> getHeroById(Long id);
    List<HeroEntity> getHeroesByName(String name);
    HeroEntity createHero(HeroEntity hero);
    HeroEntity updateHero(Long id, HeroEntity hero);
    void deleteHero(Long id);
}
