package com.project.recipe.recipe.service;

import com.project.recipe.recipe.dto.RecipeDto;
import com.project.recipe.recipe.entity.recipe.RecipeIngredientModel;
import com.project.recipe.recipe.entity.recipe.RecipeInstructionModel;
import com.project.recipe.recipe.entity.recipe.RecipeModel;
import java.util.List;

public interface RecipeService {

    public List<RecipeDto> getAllRecipes();

    public RecipeDto getRecipeById(Long recipeId);

    public RecipeDto deleteRecipeById(Long recipeId);

    public RecipeDto createRecipe(RecipeDto recipe);

    public RecipeDto updateRecipe(Long recipeId,RecipeDto recipe);

    public List<RecipeInstructionModel> getRecipeInstructions(Long recipeId);

    public List<RecipeIngredientModel>  getRecipeIngredients(Long recipeId);
}
