package com.project.recipe.recipe.dto;

import com.project.recipe.recipe.entity.recipe.RecipeIngredientModel;
import com.project.recipe.recipe.entity.recipe.RecipeInstructionModel;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RecipeDto {

    private long recipeId;

    @NotEmpty(message = "recipe name cannot be empty!")
    private String recipeName;

    private String creationDate;

    @NotEmpty(message="vegan choice could not be empty!")
    @NotNull
    private String veganYN;

    @NotNull
    @PositiveOrZero
    private Integer suitableCount;

    @NotEmpty(message="ingredients could not be empty!")
    @NotNull
    private Set<RecipeIngredientModel> recipeIngredientsList=new HashSet<>();

    @NotEmpty(message="instructions could not be empty!")
    @NotNull
    private Set<RecipeInstructionModel> recipeInstructionModelList=new HashSet<>();
}
