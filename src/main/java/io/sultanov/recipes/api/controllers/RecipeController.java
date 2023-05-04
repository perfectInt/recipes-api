package io.sultanov.recipes.api.controllers;

import io.sultanov.recipes.api.services.RecipeService;
import io.sultanov.recipes.models.Recipe;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recipes")
public class RecipeController {
    private final RecipeService recipeService;

    @PostMapping("/new")
    public Long postRecipe(@Valid @RequestBody Recipe recipe) {
        return recipeService.newRecipe(recipe);
    }

    @GetMapping("/{id}")
    public Recipe getRecipeById(@PathVariable Long id) {
        return recipeService.getRecipeById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Recipe updateRecipe(@Valid @RequestBody Recipe recipe, @PathVariable Long id) {
        return recipeService.updateRecipe(recipe, id);
    }

    @GetMapping(value = "/search", produces = "application/json")
    public List<Recipe> searchRecipes(@RequestParam(name = "category", required = false) Optional<String> category,
                                      @RequestParam(name = "name", required = false) Optional<String> name) {
        return recipeService.searchRecipe(category, name);
    }
}