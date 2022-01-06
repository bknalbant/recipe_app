package com.project.recipe.recipe.entity.recipe;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="recipe_ingredients")
public class RecipeIngredientModel {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ingredient_id;

    @Column(name = "ingredient_name",length = 100,nullable = false)
    @NotEmpty(message="RecipeIngredientModel-> ingredientName could not be empty!")
    private String ingredientName;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="recipe_id",nullable = false)
    @JsonIgnore
    private RecipeModel recipe;
}
