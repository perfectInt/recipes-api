package io.sultanov.recipes.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.sultanov.recipes.exceptions.ObjectNotFoundException;
import io.sultanov.recipes.models.Recipe;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RecipeControllerTest {
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    Recipe recipe = new Recipe(
            "Pirogg",
            "Tasty pirogg",
            "Pie",
            List.of("Egg", "Smth"),
            List.of("1. make it", "2. Eat it")
    );
    Long recipeId;

    @Test
    @Order(1)
    @DisplayName("Test if recipe is getting created")
    public void postRecipeTest() throws Exception {
        RequestBuilder requestBuilderPost = MockMvcRequestBuilders.post("/api/recipes/new")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(recipe));

        MvcResult mvcResult = mockMvc.perform(requestBuilderPost).andReturn();
        String response = mvcResult.getResponse().getContentAsString();

        assertNotNull(response);
        recipeId = Long.parseLong(response);
        recipe.setId(recipeId);
    }

    @Test
    @Order(2)
    @DisplayName("Test if newly created recipe gets returned")
    public void getRecipeTest() throws Exception {
        RequestBuilder requestBuilderGet = MockMvcRequestBuilders.get("/api/recipes/" + recipeId)
                .accept(MediaType.APPLICATION_JSON_VALUE);

        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        MvcResult mvcResult = mockMvc.perform(requestBuilderGet).andReturn();
        String response = mvcResult.getResponse().getContentAsString();

        Recipe returnedRecipe = mapper.readValue(response, Recipe.class);
        assertNotNull(returnedRecipe.getDate());
        assertEquals(recipe.getName(), returnedRecipe.getName());
    }

    @Test
    @Order(3)
    @DisplayName("Test if recipe gets updated")
    public void updateRecipeTest() throws Exception {
        recipe.setName("New name");
        RequestBuilder requestBuilderPut = MockMvcRequestBuilders.put("/api/recipes/" + recipeId)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(recipe));

        MvcResult mvcResult = mockMvc.perform(requestBuilderPut).andReturn();
        String response = mvcResult.getResponse().getContentAsString();

        Recipe returnedRecipe = mapper.readValue(response, Recipe.class);
        assertEquals("New name", returnedRecipe.getName());
    }

    @Test
    @Order(4)
    @DisplayName("Test if recipe gets deleted")
    public void deleteRecipeTest() throws Exception {
        RequestBuilder requestBuilderGet = MockMvcRequestBuilders.get("/api/recipes/" + recipeId)
                .accept(MediaType.APPLICATION_JSON_VALUE);

        RequestBuilder requestBuilderDelete = MockMvcRequestBuilders.delete("/api/recipes/" + recipeId);
        mockMvc.perform(requestBuilderDelete);

        MvcResult mvcResult = mockMvc.perform(requestBuilderGet).andReturn();
        ObjectNotFoundException exception = (ObjectNotFoundException) mvcResult.getResolvedException();

        assertNotNull(exception);
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }
}
