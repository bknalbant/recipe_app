package com.project.recipe.recipe.utils;

import com.project.recipe.recipe.dto.RecipeDto;
import com.project.recipe.recipe.entity.recipe.RecipeModel;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ObjectMapper {


    public static RecipeDto mapRecipeEntityToRecipeDto(RecipeModel entity){
        RecipeDto dto= RecipeDto.builder().build();
        BeanUtils.copyProperties(entity,dto);
        dto.setCreationDate(DateUtil.createFormattedStringDate(entity.getCreationDate()));
        return dto;
    }

    public static List<RecipeDto> listRecipeEntityToRecipeDto(List<RecipeModel> entityList){
        if(!CollectionUtils.isEmpty(entityList)){
            List<RecipeDto> recipeDtoList=new ArrayList<>();
            for (RecipeModel  entity : entityList) {
                recipeDtoList.add(mapRecipeEntityToRecipeDto(entity));
            }
            return recipeDtoList;
        }
        return Collections.emptyList();
    }

    public static List<RecipeModel> listRecipeDtoToRecipeEntity(List<RecipeDto> recipeDtoList){
        if(!CollectionUtils.isEmpty(recipeDtoList)){
            List<RecipeModel> recipeEntityList=new ArrayList<>();
            for (RecipeDto  dto : recipeDtoList) {
                recipeEntityList.add(mapRecipeModelToRecipeModel(dto));
            }
            return recipeEntityList;
        }
        return Collections.emptyList();
    }

    public static RecipeModel mapRecipeModelToRecipeModel(RecipeDto dto){
        RecipeModel entity= RecipeModel.builder().build();
        BeanUtils.copyProperties(dto,entity);
        return entity;
    }


}
