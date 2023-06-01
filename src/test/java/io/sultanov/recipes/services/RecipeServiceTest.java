package io.sultanov.recipes.services;

import io.sultanov.recipes.api.services.RecipeService;
import io.sultanov.recipes.api.services.UserService;
import io.sultanov.recipes.models.Recipe;
import org.junit.Ignore;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext
public class RecipeServiceTest {
    @Autowired
    RecipeService recipeService;

    @Autowired
    UserService userService;

    Recipe recipe1 = new Recipe(
            "Pirogg",
            "Tasty pirogg",
            "Pie",
            List.of("Egg", "Smth"),
            List.of("1. make it", "2. Eat it")
    );

    Recipe recipe2 = new Recipe(
            "apple pie",
            "Tasty apple pie",
            "Pie",
            List.of("Egg", "Smth", "apple"),
            List.of("1. make it", "2. Eat it")
    );

    @Test
    @Order(1)
    @Disabled
    @DisplayName("Test if search works correctly")
    public void searchRecipesTest() {
        recipe1.setId(recipeService.newRecipe(recipe1));
        recipe2.setId(recipeService.newRecipe(recipe2));

        List<Recipe> recipes = recipeService.searchRecipe(Optional.of("Pie"), Optional.empty());
        assertEquals(2, recipes.size());

        recipes = recipeService.searchRecipe(Optional.empty(), Optional.of("apple"));
        assertEquals(1, recipes.size());
    }

    @Test
    @Order(2)
    @DisplayName("Test if there 2 or 0 request params")
    public void unexpectedAmountOfParamsTest() {
        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () ->
                recipeService.searchRecipe(Optional.empty(), Optional.empty()));
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());

        thrown = assertThrows(ResponseStatusException.class, () ->
                recipeService.searchRecipe(Optional.of("lala"), Optional.of("lala")));
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
    }
}
