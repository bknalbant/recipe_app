package com.project.recipe.recipe.repository;

import com.project.recipe.recipe.entity.recipe.RecipeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<RecipeModel,Long> {

    public Optional<RecipeModel> findByRecipeName(@Param("recipeName") String recipeName);

}
