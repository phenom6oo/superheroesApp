package com.superheroes.SuperheroesApp.models.services;

import com.superheroes.SuperheroesApp.models.aspect.Timed;
import com.superheroes.SuperheroesApp.models.exceptions.NotFoundException;
import com.superheroes.SuperheroesApp.models.models.HeroEntity;
import com.superheroes.SuperheroesApp.models.repositories.HeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
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
    @Cacheable("heroesCache")
    @Timed
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
    @Timed
    @CacheEvict(value = "heroesCache", allEntries = true)
    @Override
    public HeroEntity createHero(HeroEntity hero) {
        return heroRepository.save(hero);
    }

    @Timed
    @Override
    public HeroEntity updateHero(Long id, HeroEntity hero) {
        if (heroRepository.existsById(id)) {
            hero.setId(id);
            return heroRepository.save(hero);
        } else {
            throw new NotFoundException("Hero not found with ID: " + id);
        }
    }

    @Override
    public void deleteHero(Long id) {
        try {
            heroRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new NotFoundException("Hero not found with ID: " + id);
        }
    }
}
