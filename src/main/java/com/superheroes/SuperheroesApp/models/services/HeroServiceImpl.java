package com.superheroes.SuperheroesApp.models.services;

import com.superheroes.SuperheroesApp.models.models.HeroEntity;
import com.superheroes.SuperheroesApp.models.repositories.HeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HeroServiceImpl implements HeroService {

    private final HeroRepository heroRepository;

    @Autowired
    public HeroServiceImpl(HeroRepository heroRepository) {
        this.heroRepository = heroRepository;
    }

    @Override
    public List<HeroEntity> getAllHeroes() {
        return heroRepository.findAll();
    }

    @Override
    public Optional<HeroEntity> getHeroById(Long id) {
        return heroRepository.findById(id);
    }

    @Override
    public List<HeroEntity> getHeroesByName(String name) {
        return heroRepository.findByName(name);
    }

    @Override
    public HeroEntity createHero(HeroEntity hero) {
        return heroRepository.save(hero);
    }

    @Override
    public HeroEntity updateHero(Long id, HeroEntity hero) {
        if (heroRepository.existsById(id)) {
            hero.setId(id);
            return heroRepository.save(hero);
        } else {
            throw new IllegalArgumentException("Hero not found with ID: " + id);
        }
    }

    @Override
    public void deleteHero(Long id) {
        heroRepository.deleteById(id);
    }
}
