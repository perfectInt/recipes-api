package io.sultanov.recipes.api.services;

import io.sultanov.recipes.api.repos.RecipeRepository;
import io.sultanov.recipes.exceptions.ObjectNotFoundException;
import io.sultanov.recipes.models.Recipe;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;

    public Long newRecipe(Recipe recipe) {
        recipe.setDate(LocalDateTime.now());
        return recipeRepository.save(recipe).getId();
    }

    public Recipe getRecipeById(Long id) {
        return recipeRepository.findById(id).orElseThrow(ObjectNotFoundException::new);
    }

    public void deleteRecipe(Long id) {
        recipeRepository.findById(id).orElseThrow(ObjectNotFoundException::new);
        recipeRepository.deleteById(id);
    }

    public Recipe updateRecipe(Recipe recipeDto, Long id) {
        Recipe recipe = recipeRepository.findById(id).orElseThrow(ObjectNotFoundException::new);
        recipe.setDate(LocalDateTime.now());
        recipe.setCategory(recipeDto.getCategory());
        recipe.setDirection(recipeDto.getDirection());
        recipe.setIngredients(recipeDto.getIngredients());
        recipe.setDescription(recipeDto.getDescription());
        recipe.setName(recipeDto.getName());
        recipeRepository.save(recipe);
        return recipe;
    }

    public List<Recipe> searchRecipe(Optional<String> category, Optional<String> name) {
        if (category.isPresent() && name.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        else if (category.isEmpty() && name.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        if (category.isPresent())
            return searchByCategory(category.get());
        else
            return searchByName(name.get());
    }

    public List<Recipe> searchByCategory(String category) {
        return recipeRepository.findAllByCategoryIgnoreCaseOrderByDateDesc(category);
    }

    public List<Recipe> searchByName(String name) {
        return recipeRepository.findAllByNameContainingIgnoreCaseOrderByDateDesc(name);
    }
}
