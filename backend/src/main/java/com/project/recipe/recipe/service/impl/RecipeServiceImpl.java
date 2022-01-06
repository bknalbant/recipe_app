package com.project.recipe.recipe.service.impl;

import com.project.recipe.recipe.dto.RecipeDto;
import com.project.recipe.recipe.entity.recipe.RecipeIngredientModel;
import com.project.recipe.recipe.entity.recipe.RecipeInstructionModel;
import com.project.recipe.recipe.entity.recipe.RecipeModel;
import com.project.recipe.recipe.exception.RecipeAlreadyExistException;
import com.project.recipe.recipe.exception.RecipeNotFoundException;
import com.project.recipe.recipe.repository.RecipeRepository;
import com.project.recipe.recipe.service.RecipeService;
import com.project.recipe.recipe.utils.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    @Override
    public List<RecipeDto> getAllRecipes() {
        List<RecipeModel> recipeModelList = recipeRepository.findAll();
        if(!CollectionUtils.isEmpty(recipeModelList)){
            log.info("[RecipeServiceImpl getAllRecipes()]-> this method has returned some items , size{} "+recipeModelList.size());
            return ObjectMapper.listRecipeEntityToRecipeDto(recipeModelList);
        }
        log.info("[RecipeServiceImpl getAllRecipes()]-> this method has returned anything");
        return Collections.emptyList();
    }

    @Override
    public RecipeDto getRecipeById(Long recipeId) {
        log.info("[RecipeServiceImpl getRecipeById()]-> has been called with recipe id: "+recipeId);
        RecipeModel recipeModel=recipeRepository.findById(recipeId).orElseThrow(RecipeNotFoundException::new);

        log.info("[RecipeServiceImpl getRecipeById()]-> related recipe has been founded -> recipe {id} :"+recipeId);
        return ObjectMapper.mapRecipeEntityToRecipeDto(recipeModel);
    }

    @Override
    public RecipeDto deleteRecipeById(Long recipeId) {
        log.info("[RecipeServiceImpl deleteRecipeById()]-> has been called with recipe id: "+recipeId);
        RecipeModel recipeModel= recipeRepository.findById(recipeId).orElseThrow(RecipeNotFoundException::new);

        log.info("[RecipeServiceImpl deleteRecipeById()]-> before removing , recipe has been founded");
        recipeRepository.delete(recipeModel);

        log.info("[RecipeServiceImpl deleteRecipeById()]-> after removing , operation has been successful");
        return ObjectMapper.mapRecipeEntityToRecipeDto(recipeModel);
    }

    @Override
    public RecipeDto createRecipe(RecipeDto recipeDto){
        log.info("[RecipeServiceImpl createRecipe()]-> has been called ");
        RecipeModel recipeModel = ObjectMapper.mapRecipeModelToRecipeModel(recipeDto);

        recipeRepository.findByRecipeName(recipeModel.getRecipeName()).ifPresent(recipe1->{throw new RecipeAlreadyExistException();});
        log.info("[RecipeServiceImpl createRecipe()]-> recipe has not  been founded , it will be created ");

        addSomeRequieredData(recipeModel);
        addCreationDate(recipeModel);
        RecipeModel savedModel = recipeRepository.save(recipeModel);

        log.info("[RecipeServiceImpl createRecipe()]->    recipe has been created , recipe {name } :"+savedModel.getRecipeName());
        log.info("[RecipeServiceImpl createRecipe()]->  , created recipe  {id} is :"+savedModel.getRecipeId());

        return ObjectMapper.mapRecipeEntityToRecipeDto(savedModel);
    }

    @Override
    public RecipeDto updateRecipe(Long recipeId, RecipeDto recipeDto) {
        log.info("[RecipeServiceImpl updateRecipe()]-> has been called ");
        RecipeModel model =  recipeRepository.findById(recipeId).orElseThrow(RecipeNotFoundException::new);

        log.info("[RecipeServiceImpl updateRecipe()]-> recipe has been found , it will be updated ");
        BeanUtils.copyProperties(recipeDto,model);
        addSomeRequieredData(model);

        log.info("[RecipeServiceImpl updateRecipe()]-> properties have been copied");
        recipeRepository.save(model);

        log.info("[RecipeServiceImpl updateRecipe()]-> recipe has been updated ");
        return ObjectMapper.mapRecipeEntityToRecipeDto(model);
    }

    @Override
    public List<RecipeInstructionModel> getRecipeInstructions(Long recipeId) {
        log.info("[RecipeServiceImpl getRecipeInstructions()]-> has been called with this recipe id :"+recipeId);
        RecipeDto model=getRecipeById(recipeId);
        log.info("[RecipeServiceImpl getRecipeInstructions()]-> getRecipeInstructions has been invoked");

        Set<RecipeInstructionModel> recipeInstructionModelList = model.getRecipeInstructionModelList();
        if(CollectionUtils.isEmpty(recipeInstructionModelList)){
            log.info("[RecipeServiceImpl getRecipeInstructions()]-> instructions list are just empty!");
            return Collections.emptyList();
        }
        log.info("[RecipeServiceImpl getRecipeInstructions()]-> instructions size of list are : "+recipeInstructionModelList.size());
        return recipeInstructionModelList.stream().collect(Collectors.toList());
    }

    @Override
    public List<RecipeIngredientModel> getRecipeIngredients(Long recipeId) {
        log.info("[RecipeServiceImpl getRecipeIngredients()]-> has been called with this recipe id :"+recipeId);
        RecipeDto model=getRecipeById(recipeId);

        log.info("[RecipeServiceImpl getRecipeIngredients()]->  has been invoked");
        Set<RecipeIngredientModel> recipeIngredientsList = model.getRecipeIngredientsList();

        if(CollectionUtils.isEmpty(recipeIngredientsList)){
            log.info("[RecipeServiceImpl getRecipeIngredients()]-> ingredients list are just empty!");
            return Collections.emptyList();
        }
        log.info("[RecipeServiceImpl getRecipeIngredients()]-> ingredients size of list  are : "+recipeIngredientsList.size());
        return recipeIngredientsList.stream().collect(Collectors.toList());
    }

    private void addSomeRequieredData(RecipeModel recipeModel) {
        recipeModel.getRecipeIngredientsList().stream().forEach(m -> m.setRecipe(recipeModel));
        recipeModel.getRecipeInstructionModelList().stream().forEach(m ->m.setRecipe(recipeModel));

    }

    private void addCreationDate(RecipeModel recipeModel){
        recipeModel.setCreationDate(new Date());
    }
}
