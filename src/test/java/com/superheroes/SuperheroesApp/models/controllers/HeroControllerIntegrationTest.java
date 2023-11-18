package com.superheroes.SuperheroesApp.models.controllers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.superheroes.SuperheroesApp.models.models.HeroEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class HeroControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllHeroes() throws Exception {
        String responseBody = mockMvc.perform(MockMvcRequestBuilders.get("/api/heroes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();
        HeroEntity[] heroes = objectMapper.readValue(responseBody, HeroEntity[].class);
        assertTrue(heroes.length > 0);
    }

    @Test
    public void testCreateHero() throws Exception {
        HeroEntity heroToCreate = new HeroEntity();
        heroToCreate.setName("TestHero");

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/heroes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(heroToCreate)))
                .andExpect(status().isCreated());
         String responseBody = resultActions.andReturn().getResponse().getContentAsString();
         HeroEntity createdHero = objectMapper.readValue(responseBody, HeroEntity.class);
         assertEquals("TestHero", createdHero.getName());
    }

    @Test
    public void testGetHeroById() throws Exception {
        HeroEntity heroToCreate = new HeroEntity();
        heroToCreate.setName("TestHero");
        ResultActions creationResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/heroes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(heroToCreate)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        String creationResponseBody = creationResult.andReturn().getResponse().getContentAsString();
        HeroEntity createdHero = objectMapper.readValue(creationResponseBody, HeroEntity.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/heroes/{id}", createdHero.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(createdHero.getId()))
                .andExpect(jsonPath("$.name").value(createdHero.getName()));
    }

    @Test
    public void testUpdateHero() throws Exception {
        HeroEntity heroToCreate = new HeroEntity();
        heroToCreate.setName("TestHero");
        ResultActions creationResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/heroes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(heroToCreate)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        String creationResponseBody = creationResult.andReturn().getResponse().getContentAsString();
        HeroEntity createdHero = objectMapper.readValue(creationResponseBody, HeroEntity.class);

        createdHero.setName("UpdatedTestHero");
        mockMvc.perform(MockMvcRequestBuilders.put("/api/heroes/{id}", createdHero.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createdHero)))
                .andExpect(status().isOk())
                .andExpect(content().string("Hero updated successfully"));
    }

    @Test
    public void testDeleteHero() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/heroes/{id}", 1))
                .andExpect(status().isNoContent());
    }

}
