package com.project.recipe.recipe.entity.recipe;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import java.util.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="recipes")
public class RecipeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long recipeId;

    @Column(name="recipe_name",nullable = false)
    @NotEmpty(message = "recipe name cannot be empty!")
    private String recipeName;

    @Column(name="creation_date",nullable = false)
    private Date creationDate;

    @Column(name="vegan_yn",nullable = true , length = 1)
    @NotEmpty(message="vegan choice could not be empty!")
    @Pattern(regexp = "Y|N")
    private String veganYN;

    @Column(name="suitable_count",nullable = false)
    @NotNull
    @PositiveOrZero
    private Integer suitableCount;

    @OneToMany(mappedBy = "recipe",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @NotEmpty(message="ingredients could not be empty!")
    private Set<RecipeIngredientModel> recipeIngredientsList=new HashSet<>();

    @OneToMany(mappedBy = "recipe",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @NotEmpty(message="instructions could not be empty!")
    private Set<RecipeInstructionModel> recipeInstructionModelList=new HashSet<>();



}
