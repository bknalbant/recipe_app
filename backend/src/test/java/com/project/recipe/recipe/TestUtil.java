package com.project.recipe.recipe;

import com.project.recipe.recipe.dto.RecipeDto;
import com.project.recipe.recipe.entity.recipe.RecipeIngredientModel;
import com.project.recipe.recipe.entity.recipe.RecipeInstructionModel;
import com.project.recipe.recipe.entity.recipe.RecipeModel;
import com.project.recipe.recipe.utils.ObjectMapper;
import org.assertj.core.util.DateUtil;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class TestUtil {

    public static RecipeDto buildRecipe(){
        RecipeDto testModel=  RecipeDto.builder().
                recipeId(1).recipeName("pasta").
                creationDate(DateUtil.now().toString())
                .veganYN("Y")
                .recipeInstructionModelList(buildRecipeInstructionList())
                .recipeIngredientsList(buildRecipeIngredientList()).build();
        return testModel;
    }

    public static RecipeModel buildRecipeModel(){
        RecipeModel entityModel=  RecipeModel.builder().recipeId(1).recipeName("pasta").
                creationDate(DateUtil.now())
                .veganYN("Y")
                .recipeInstructionModelList(buildRecipeInstructionList())
                .recipeIngredientsList(buildRecipeIngredientList()).build();
        return entityModel;
    }

    public static Set<RecipeIngredientModel> buildRecipeIngredientList() {
        Set<RecipeIngredientModel> testIngredientList=new HashSet<>();
        testIngredientList.add(RecipeIngredientModel.builder().ingredient_id(1).ingredientName("water")
                .build());
        return testIngredientList;
    }

    public static Set<RecipeInstructionModel> buildRecipeInstructionList(){
        Set<RecipeInstructionModel> testInstructionList=new HashSet<>();
        testInstructionList.add(RecipeInstructionModel.builder().instruction_id(1).instructionDetail("boil pasta")
                .build());
        return testInstructionList;
    }

    public static RecipeModel convertRecipeModel(RecipeDto recipeTestModel) {
        RecipeModel recipeModelTest = ObjectMapper.mapRecipeModelToRecipeModel(recipeTestModel);
        recipeModelTest.setCreationDate(new Date());
        return recipeModelTest;
    }
}
