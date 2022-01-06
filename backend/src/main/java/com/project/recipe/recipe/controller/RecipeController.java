package com.project.recipe.recipe.controller;

import com.project.recipe.recipe.dto.RecipeDto;
import com.project.recipe.recipe.entity.recipe.RecipeIngredientModel;
import com.project.recipe.recipe.entity.recipe.RecipeInstructionModel;
import com.project.recipe.recipe.service.RecipeService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value="/api/v1/recipe")
public class RecipeController {

    private final RecipeService recipeService;


    @ApiOperation("getAllRecipes() performs to get all recipes")
    @GetMapping("/allrecipes")
    public ResponseEntity<List<RecipeDto>> getAllRecipes(){
        return new ResponseEntity<>(recipeService.getAllRecipes(), HttpStatus.OK);
    }

    @ApiOperation("getRecipeById()-> performs to get one recipe")
    @GetMapping("/{recipeId}")
    public ResponseEntity<RecipeDto> getRecipeById(@PathVariable(value = "recipeId") @NotNull Long recipeId){
        return new ResponseEntity<>(recipeService.getRecipeById(recipeId),HttpStatus.OK);
    }

    @ApiOperation("deleteRecipeById()-> performs to delete a recipe")
    @DeleteMapping("/delete/{recipeId}")
    public ResponseEntity<RecipeDto> deleteRecipeById(@PathVariable(value="recipeId") @NotNull Long recipeId){
        return new ResponseEntity<>(recipeService.deleteRecipeById(recipeId),HttpStatus.NO_CONTENT);
    }

    @ApiOperation("createRecipe()-> performs to create a recipe")
    @PostMapping("/create")
    public ResponseEntity<RecipeDto>  createRecipe(@Valid @RequestBody RecipeDto recipeDto){
        return new ResponseEntity<>(recipeService.createRecipe(recipeDto),HttpStatus.OK);
    }

    @ApiOperation("updateRecipe()-> performs to update a recipe")
    @PutMapping("/update/{recipeId}")
    public ResponseEntity<RecipeDto> updateRecipe(@PathVariable(value = "recipeId") @NotNull  Long recipeId,@Valid @RequestBody RecipeDto recipeDto){
        return new ResponseEntity<>(recipeService.updateRecipe(recipeId,recipeDto),HttpStatus.OK);
    }


    @ApiOperation("getRecipeInstructions()-> performs to get all recipe instructions")
    @GetMapping("/instructions/{recipeId}")
    public ResponseEntity<List<RecipeInstructionModel>> getRecipeInstructions(@PathVariable(value="recipeId") @NotNull  Long recipeId){
     return new ResponseEntity<>(recipeService.getRecipeInstructions(recipeId),HttpStatus.OK);
    }

    @ApiOperation("getRecipeIngredients()-> performs to get all recipe ingredients")
    @GetMapping("/ingredients/{recipeId}")
    public ResponseEntity<List<RecipeIngredientModel>> getRecipeIngredients(@PathVariable(value="recipeId")  @NotNull Long recipeId){
        return new ResponseEntity<>(recipeService.getRecipeIngredients(recipeId),HttpStatus.OK);
    }


}
