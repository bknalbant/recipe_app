package com.project.recipe.recipe;

import com.project.recipe.recipe.dto.RecipeDto;
import com.project.recipe.recipe.entity.recipe.RecipeModel;
import com.project.recipe.recipe.exception.RecipeAlreadyExistException;
import com.project.recipe.recipe.exception.RecipeNotFoundException;
import com.project.recipe.recipe.repository.RecipeRepository;
import com.project.recipe.recipe.service.RecipeService;
import com.project.recipe.recipe.service.impl.RecipeServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@RestClientTest(RecipeService.class)
public class RecipeServiceTest {

      @Mock
      private RecipeRepository recipeRepository;

      @InjectMocks
      private RecipeServiceImpl recipeService;


      @Test
      public void testGetAllRecipesWhenOneRecipePresent(){

          //given

          //build one recipe we will have this one
          RecipeDto recipeDto = TestUtil.buildRecipe();
          //build one recipe entity we will be expected of this entity list
          RecipeModel recipeModel = TestUtil.convertRecipeModel(recipeDto);
          //mock repository findAll method
          when(recipeRepository.findAll()).thenReturn(asList(recipeModel));

          //we call actual service method to get recipe model list
          List<RecipeDto> expectedList = recipeService.getAllRecipes();
          //then
          assertEquals(expectedList.size(),1);
          //verify
          verify(recipeRepository,atLeastOnce()).findAll();
      }

    @Test
    public void testGetAllRecipesWhenOneMorePresent(){

        //given

        //build  recipe models, we will be getting these
        RecipeDto recipeDto = TestUtil.buildRecipe();
        RecipeDto anotherRecipeDto = TestUtil.buildRecipe();

        //build  one more recipe entities ,  we will be expected of this entity list
        RecipeModel recipeModel = TestUtil.convertRecipeModel(recipeDto);
        RecipeModel anotherRecipeModel = TestUtil.convertRecipeModel(recipeDto);

        //mock repository findAll method
        when(recipeRepository.findAll()).thenReturn(asList(recipeModel,anotherRecipeModel));

        //we call actual service method to get recipe model list
        List<RecipeDto> expectedList = recipeService.getAllRecipes();

        //then , recipes can be available one more than
        assertEquals(expectedList.size(),2);
        //verify
        verify(recipeRepository,atLeastOnce()).findAll();
    }

    @Test
    public void testGetAllRecipesWhenNoRecipeExists(){

            //given

            //we can mock the repository findAll method to get no recipe
            when(recipeRepository.findAll()).thenReturn(emptyList());

            //when
            //we call actual service method to will be checked
            List<RecipeDto> allRecipes = recipeService.getAllRecipes();

            //then

            //we will be expecting no data existing on recipe list
            assertThat(allRecipes.size()).isEqualTo(0);

            //verify method
            verify(recipeRepository,atLeastOnce()).findAll();


    }
      @Test
      public void testRemoveRecipeWhenGivenOneRecipe(){

          // Given
          //we are going to create one recipe to be removed.
          RecipeDto recipeTestModel= TestUtil.buildRecipe();
          //we are going to create db entity to be removed.
          RecipeModel recipeModelTest= TestUtil.convertRecipeModel(recipeTestModel);

          //we could mock repository related method should remove one recipe.
          when(recipeRepository.findById(recipeTestModel.getRecipeId())).thenReturn(Optional.of(recipeModelTest));

          //when

          //we call actual service method then we are getting expected to be removed one recipe.
          RecipeDto resultRecipeDto = recipeService.deleteRecipeById(recipeTestModel.getRecipeId());

          //then

          //checking data integrity.
          assertEquals(resultRecipeDto.getRecipeId(),recipeModelTest.getRecipeId());
          assertFalse(recipeModelTest.getRecipeIngredientsList().isEmpty());
          assertFalse(recipeModelTest.getRecipeInstructionModelList().isEmpty());

          //verify related repository method.
          verify(recipeRepository,atLeastOnce()).findById(recipeTestModel.getRecipeId());


      }

      @Test(expected = RecipeNotFoundException.class)
      public void testRemoveRecipeWhenNoRecipePresent(){

          //given

          //we create recipeId will be removed.
          Long recipeId = TestUtil.buildRecipe().getRecipeId();

          //we could mock the repository's method.
          when(recipeRepository.findById(recipeId)).thenReturn(Optional.empty());

          //when
          //we call actual method after we are getting expected recipeNotFoundException.
          recipeService.deleteRecipeById(recipeId);

          //then
          //verifying repository method has been called.
          verify(recipeRepository,atLeastOnce()).findById(recipeId);

      }

      @Test
      public void testInsertRecipeWhenCallWithOneRecipe(){
          //given

          //build recipe dto is relating to service input
          RecipeDto recipeDto = TestUtil.buildRecipe();
          String recipeName=recipeDto.getRecipeName();
          //build recipeEntity
          RecipeModel recipeModel = TestUtil.buildRecipeModel();

          //mock some repository services
          when(recipeRepository.save(any(RecipeModel.class))).thenReturn(recipeModel);
          when(recipeRepository.findByRecipeName(anyString())).thenReturn(Optional.empty());
          //when --call insert recipe ,how it can behave , we will decide
          RecipeDto savedRecipe = recipeService.createRecipe(recipeDto);

          //then --check some fields are equals.
          assertNotNull(savedRecipe);
          assertEquals(savedRecipe.getRecipeId(),recipeDto.getRecipeId());
          assertFalse(savedRecipe.getRecipeIngredientsList().isEmpty());
          assertFalse(savedRecipe.getRecipeInstructionModelList().isEmpty());
          assertEquals(savedRecipe.getRecipeName(),recipeDto.getRecipeName());

          //verify methods has never invoked.

          verify(recipeRepository,atLeastOnce()).findByRecipeName(recipeName);
          verify(recipeRepository,atLeastOnce()).save(any(RecipeModel.class));


      }

      @Test (expected= RecipeAlreadyExistException.class)
      public void testInsertRecipeWhenRecipeAlreadyPresent(){

          //given

          //build recipe dto is relating to service input
          RecipeDto recipeDto = TestUtil.buildRecipe();
          String recipeName=recipeDto.getRecipeName();
          //build recipeEntity
          RecipeModel recipeModel = TestUtil.buildRecipeModel();


          //when recipe name finder method , how will it behave
          when(recipeRepository.findByRecipeName(anyString())).thenReturn(Optional.of(recipeModel));

          //call insert service , and we are waiting to get already present exception.

          recipeService.createRecipe(TestUtil.buildRecipe());

          //verify method actually is called

          verify(recipeRepository,atLeastOnce()).findByRecipeName(recipeName);
      }

      @Test
      public void testGetRecipeByIdWhenMethodCalledById(){

          //given

          //we created one recipe to be returned as entity
          RecipeDto recipeDto = TestUtil.buildRecipe();
          //we created a recipe entity that could be existed.
          RecipeModel recipeModel = TestUtil.convertRecipeModel(recipeDto);

          //we mock repository findRecipe method to get that one
          when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipeModel));

          //when

          //we call actual method to get recipe

          RecipeDto returnedRecipeDto = recipeService.getRecipeById(anyLong());

          //then
          //checking some data integrity.

          assertEquals(recipeDto.getRecipeName(),returnedRecipeDto.getRecipeName());
          assertEquals(recipeDto.getRecipeId(),returnedRecipeDto.getRecipeId());

          //verify

          verify(recipeRepository,atLeastOnce()).findById(anyLong());

      }

      @Test(expected = RecipeNotFoundException.class)
      public void testGetRecipeByIdWhenNoRecipePresent(){

          //given

          //we created recipe id that could be searched.
          Long recipeId = TestUtil.buildRecipe().getRecipeId();

          //we mock repository method to throw no recipe founded exception
          when(recipeRepository.findById(recipeId)).thenReturn(Optional.empty());

          //then
          //we will call actual method to be expected no recipe found exception.
          recipeService.getRecipeById(recipeId);

          //verify

          verify(recipeRepository,atLeastOnce()).findById(recipeId);
      }


      @Test
      public void testUpdateRecipeWhenGivenOneRecipe(){

          //given

          //we created a recipe dto that would be updated.
          RecipeDto recipeDto = TestUtil.buildRecipe();
          Long recipeId = recipeDto.getRecipeId();
          //we created a recipe entity that we would be expected
          RecipeModel recipeModel = TestUtil.convertRecipeModel(recipeDto);


          //we mock recipe update service.
          when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipeModel));

          //when
          //we call actual service method to update related recipe
          RecipeDto returnedRecipe = recipeService.updateRecipe(recipeId, recipeDto);

          //then
          assertEquals(recipeDto.getRecipeName(),returnedRecipe.getRecipeName());
          //verify

          verify(recipeRepository,atLeastOnce()).findById(recipeId);


      }

    @Test(expected = RecipeNotFoundException.class)
    public void testUpdateRecipeWhenNoRecipePresent(){

        //given

        //we created a recipe dto that would be updated.
        RecipeDto recipeDto = TestUtil.buildRecipe();
        Long recipeId = recipeDto.getRecipeId();

        //we mock repository method to throw no recipe founded exception
        when(recipeRepository.findById(recipeId)).thenReturn(Optional.empty());

        //then
        //we will call actual method to be expected no recipe found exception.
        recipeService.updateRecipe(recipeId,recipeDto);

        //verify

        verify(recipeRepository,atLeastOnce()).findById(recipeId);
    }




}
