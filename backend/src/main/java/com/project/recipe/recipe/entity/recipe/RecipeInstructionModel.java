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
@Table(name="recipe_instructions")
public class RecipeInstructionModel {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long instruction_id;

    @Column(name = "instruction_detail",nullable = false)
    @NotEmpty(message="RecipeInstructionModel-> instructionDetail could not be empty!")
    private String instructionDetail;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="recipe_id",nullable = false)
    @JsonIgnore
    private RecipeModel recipe;

}
