package com.superheroes.SuperheroesApp.models.repositories;

import com.superheroes.SuperheroesApp.models.models.HeroEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HeroRepository extends JpaRepository<HeroEntity, Long> {
    List<HeroEntity> findByName(String name);
}
