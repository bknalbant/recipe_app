import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useParams ,useNavigate} from 'react-router-dom';
import { selectedRecipe, removeSelectedRecipe } from '../redux/actions/recipeActions';
import {authToken} from './utils/authToken';


const RecipeDetail = () => {

  if (localStorage.jwtToken) {
    console.log('jwt token',localStorage.jwtToken);
    authToken(localStorage.jwtToken);
 }

  const { recipeId } = useParams();
  let recipe = useSelector((state) => state.recipe);
  const { recipeName, creationDate, veganYN, recipeIngredientsList, recipeInstructionModelList,suitableCount } = recipe;
  const dispatch = useDispatch();
  console.log('recipeId', recipeId);

  const [editIndex, setEditIndex] = useState(-1);
  const navigate = useNavigate();

  const fetchRecipeDetail = async (id) => {
    const response = await axios.get(`http://localhost:8080/api/v1/recipe/${id}`,{withCredentials: true}).catch((err) => {
      console.log('err', err);
    });
    dispatch(selectedRecipe(response.data));
  };

  useEffect(() => {
    if (localStorage.jwtToken) {
      if (recipeId && recipeId !== "") fetchRecipeDetail(recipeId);
      return () => {
        dispatch(removeSelectedRecipe());
      };
    }else{
      navigate('/');
    }
   
  }, [recipeId]);

  const onChangeRecipe = (event) => {
    recipe.recipeName = event.target.value;
    dispatch(selectedRecipe(recipe));
  }

  const onChangeCreationDate = (event) => {
    recipe.creationDate = event.target.value;
    dispatch(selectedRecipe(recipe));
  }

  const onChangeVeganYN = (event) => {
    console.log('change event checkbox', event.target.checked);
    recipe.veganYN = event.target.checked ? 'Y' : 'N';
    dispatch(selectedRecipe(recipe));
  }

  const handleChangeInstruction = (id, detail) => {
    document.getElementById("instruction-name").value = detail;
    setEditIndex(id);

  }

  const handleChangeIngredient = (id, detail) => {
    document.getElementById("ingredient-name").value = detail;
    setEditIndex(id);

  }

  const handleIngredientClick = () => {
    const newValue = document.getElementById("ingredient-name").value;
    if (newValue) {
      if (editIndex !== -1) {
        console.log('new value ingredient', newValue);
        console.log('edit index ', editIndex);

        let x = recipe.recipeIngredientsList.find(x => x.ingredient_id === editIndex).ingredientName = newValue;
        console.log('ingredient value has been changed ');
        dispatch(selectedRecipe(recipe));
        document.getElementById("ingredient-name").value = '';
        setEditIndex(-1);
      } else {
        let object = {};
        object.ingredient_id = getRandomInt(300);
        object.ingredientName = newValue;
        recipe.recipeIngredientsList.push(object);
        dispatch(selectedRecipe(recipe));
        document.getElementById("ingredient-name").value = '';
        setEditIndex(-1);
        console.log('ingredient value has been added.');
      }

    }

  }

  const handleInstructionClick = () => {
    const newValue = document.getElementById("instruction-name").value;
    if (newValue) {
      if (editIndex !== -1) {
        console.log('new value instruction', newValue);
        console.log('edit index ', editIndex);

        let x = recipe.recipeInstructionModelList.find(x => x.instruction_id === editIndex).instructionDetail = newValue;
        console.log('deger guncellendi');
        dispatch(selectedRecipe(recipe));
        document.getElementById("instruction-name").value = '';
        setEditIndex(-1);
      } else {
        let object = {};
        object.instruction_id = getRandomInt(300);
        object.instructionDetail = newValue;
        recipe.recipeInstructionModelList.push(object);
        dispatch(selectedRecipe(recipe));
        document.getElementById("instruction-name").value = '';
        setEditIndex(-1);
        console.log('new element is added');
      }

    }

  }

  function getRandomInt(max) {
    return Math.floor(Math.random() * max);
  }

  const updateRecipeItem = (e) => {
      e.preventDefault();
      console.log('event prevented');
      if(recipe.recipeId===null) return;

      const element = document.getElementById('showInfoMessage');
      console.log('recipe will update',recipe);
      
      axios.put(`http://localhost:8080/api/v1/recipe/update/${recipe.recipeId}`, recipe,{withCredentials: true})
        .then(response => {element.innerHTML='Update operation has been successfuly.';
        navigateHome();
        })
        .catch(error => {
          element.innerHTML = `Error: ${error.message}`;
          console.error('There was an error!', error);
        });

  }

  const deleteRecipeItem = (e)=>{
     e.preventDefault();
    console.log('event prevented');

    const element = document.getElementById('showInfoMessage');
    console.log('recipe will delete',recipe);

    axios.delete(`http://localhost:8080/api/v1/recipe/delete/${recipe.recipeId}`,{withCredentials: true})
        .then(response => {
          element.innerHTML='Delete operation has been successfuly.';
          console.log('recipe',recipe);
          navigateHome();
        })
        .catch(error => {
          element.innerHTML = `Error: ${error.message}`;
          console.error('There was an error!', error);
        });

  }
  const actionChangeSuitableCount=(e)=>{
    console.log('change event suitable count', e.target.checked);
    recipe.suitableCount = e.target.value;
    dispatch(selectedRecipe(recipe));
  }

  const navigateHome=()=>{
    setTimeout(function () {
      navigate("/home");
  }, 1000);
  }

  return (
    <div className="ui raised very padded text container segment">
      {
        Object.keys(recipe).length === 0 ? (
          <div>...No Data Found</div>
        ) : (

          <form className="ui form" onSubmit={updateRecipeItem}>
            <div className="field">
              <label>Recipe Name</label>
              <input type="text" autoComplete='off' name="recipe-name" placeholder="Recipe Name" value={recipeName} onChange={onChangeRecipe} />
            </div>
            <div className="field ">
              <label>Creation Date</label>
              <div class="ui disabled input">
               <input  type="text" autoComplete='off' name="creation-date" placeholder="Creation Date" value={creationDate} onChange={onChangeCreationDate} />
              </div>
            </div>
            <div className="field ">
              <label>Number Of Suitable Count</label>
              <div class="ui  input">
                <input type="text" autoComplete='off' name="suitableCount" placeholder="Enter suitable count" value={recipe.suitableCount} onChange={actionChangeSuitableCount} />
              </div>
            </div>          
            <div className="field">
              <div className="ui checkbox">
                <input type="checkbox" tabindex="0" defaultChecked={veganYN == 'Y' ? true : false} onChange={onChangeVeganYN} />
                <label>Is Vegan</label>
              </div>
            </div>
            <div className="field">
              <label>Instructions</label>
              <div className="ui grid">
                <div className="two wide column"></div>
                <div className="ten wide column">
                  <input type="text" autoComplete='off' name="instruction-name" id="instruction-name" placeholder="Enter Instruction " />
                </div>
                <div className="two wide column">
                  <input type="button" className="ui button" value="Add" onClick={() => handleInstructionClick()} />
                </div>
              </div>
              <div class="ui grid" style={{marginTop:'2px'}}>
                <div class="ten center wide column">
                  <div class="ignored ui info message" id="showInfoMessage" style={{height:'40px'}}>
                      You can pick a instruction up below list and edit it.
                  </div>  
                </div>
              </div>
              <br />
              <ul className="ui list">
                {recipeInstructionModelList.map((instruction) => {
                  const { instruction_id, instructionDetail } = instruction;
                  return (
                    <li key={instruction_id} onClick={() => handleChangeInstruction(instruction_id, instructionDetail)}>{instructionDetail}</li>
                  );
                })}
              </ul>

            </div>
            <div className="field">
              <label>Ingredients</label>
              <div className="ui grid">
                <div className="two wide column"></div>
                <div className="ten wide column">
                  <input type="text" autoComplete='off' name="ingredient-name" id="ingredient-name" placeholder="Enter Ingredient " />
                </div>
                <div className="two wide column">
                  <input type="button" className="ui button" value="Add" onClick={() => handleIngredientClick()} />
                </div>
              </div>
              <div class="ui grid" style={{marginTop:'2px'}}>
                <div class="ten center wide column">
                  <div class="ignored ui info message" id="showInfoMessage" style={{height:'40px'}}>
                      You can pick a ingredient up below list and edit it.
                  </div>  
                </div>
              </div>
              <ul className="ui list">
                {recipeIngredientsList.map((ingredient) => {
                  const { ingredient_id, ingredientName } = ingredient;
                  return (
                    <li className="item" key={ingredient_id} onClick={() => handleChangeIngredient(ingredient_id, ingredientName)}>{ingredientName}</li>
                  );
                })}
              </ul>

            </div>
            <br/>
            <div className='ui grid'>
              <div className='twelve wide column'></div>
            <input type="button" className="ui right floated red button"  onClick={deleteRecipeItem} value="Delete"/>
            <button className="ui right floated primary button" type="submit">Update</button>
            </div>
            <br/><br/>
            <div class="ignored ui info message" id="showInfoMessage">

            </div>
          </form>

        )
      }
    </div>
  );
};

export default RecipeDetail;