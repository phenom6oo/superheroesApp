package com.superheroes.SuperheroesApp.models.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.superheroes.SuperheroesApp.models.models.AuthRequest;
import com.superheroes.SuperheroesApp.models.models.HeroEntity;
import com.superheroes.SuperheroesApp.models.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
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

@SpringBootTest
@AutoConfigureMockMvc
public class HeroControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private String authToken;

    @BeforeEach
    public void setup() throws Exception {
        AuthRequest authRequest = new AuthRequest("jona", "password");
        String authRequestBody = objectMapper.writeValueAsString(authRequest);

        ResultActions authResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(authRequestBody));

        authToken = authResult.andReturn().getResponse().getContentAsString();
    }

    @Test
    public void testCreateHerow() throws Exception {
        HeroEntity heroToCreate = new HeroEntity();
        heroToCreate.setName("TestHero");

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/heroes")
                        .header("token", authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(heroToCreate)))
                .andExpect(status().isCreated());

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        HeroEntity createdHero = objectMapper.readValue(responseBody, HeroEntity.class);
        assertEquals("TestHero", createdHero.getName());
    }

    @Test
    public void testGetAllHeroes() throws Exception {
        String responseBody = mockMvc.perform(MockMvcRequestBuilders.get("/api/heroes")
                        .header("token", authToken) )
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
                        .header("token", authToken)
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
                        .header("token", authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(heroToCreate)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        String creationResponseBody = creationResult.andReturn().getResponse().getContentAsString();
        HeroEntity createdHero = objectMapper.readValue(creationResponseBody, HeroEntity.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/heroes/{id}", createdHero.getId())
                        .header("token", authToken) )
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
                        .header("token", authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(heroToCreate)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        String creationResponseBody = creationResult.andReturn().getResponse().getContentAsString();
        HeroEntity createdHero = objectMapper.readValue(creationResponseBody, HeroEntity.class);

        createdHero.setName("UpdatedTestHero");
        mockMvc.perform(MockMvcRequestBuilders.put("/api/heroes/{id}", createdHero.getId())
                        .header("token", authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createdHero)))
                .andExpect(status().isOk())
                .andExpect(content().string("Hero updated successfully"));
    }

    @Test
    public void testDeleteHero() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/heroes/{id}", 1)
                        .header("token", authToken) )
                .andExpect(status().isNoContent());
    }

}
