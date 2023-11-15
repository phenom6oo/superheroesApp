package com.superheroes.SuperheroesApp.models.controllers;
import com.superheroes.SuperheroesApp.models.models.HeroEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/heroes")
public class HeroController {

    @GetMapping
    public List<HeroEntity> getAllHeroes() {
        return null;
    }

    @GetMapping("/saludo")
    public String getHelloWorld() {
        return "Hola Mundo";
    }
}
