import React, { useState,useEffect } from 'react';
import axios from 'axios';
import {authToken} from './utils/authToken';
import { useNavigate} from 'react-router-dom';
 

const RecipeInsert = () => {

  if (localStorage.jwtToken) {
    console.log('jwt token',localStorage.jwtToken);
    authToken(localStorage.jwtToken);
  }


  const [recipe, setRecipe] = useState({ recipeId: null, recipeName: '', creationDate: '', suitableCount:'',veganYN: '' });
  const [instructions, setInstructions] = useState([]);
  const [ingredients, setIngredients] = useState([]);

  const [editIndex, setEditIndex] = useState(-1);
  const navigate = useNavigate();

  useEffect(()=>{
    if (!localStorage.jwtToken) {
      navigate('/');
    } 
   },[]);

  const handleInstructionClick = () => {

    const newValue = document.getElementById("instruction-name").value;
    if (newValue) {
      if (editIndex !== -1) {
        console.log('new value instruction', newValue);
        console.log('edit index ', editIndex);

        let x = instructions.find(x => x.instruction_id === editIndex).instructionDetail = newValue;
        console.log('deger guncellendi');
        document.getElementById("instruction-name").value = '';
        setEditIndex(-1);
        console.log(instructions);
      } else {
        let object = {};
        object.instruction_id = getRandomInt(300);
        object.instructionDetail = newValue;

        setInstructions([...instructions, object]);

        document.getElementById("instruction-name").value = '';
        setEditIndex(-1);

      }

    }

  }

  function getRandomInt(max) {
    return Math.floor(Math.random() * max);
  }

  const handlerOnChangeInput=(e)=>{
    const { name, value } = e.target;
    setRecipe({ ...recipe, [name]: value });
  }

  const onChangeVeganYN = (e) => {
    console.log('change event checkbox', e.target.checked);
    let isvegan = e.target.checked ? 'Y' : 'N';

    setRecipe(() => ({
      ...recipe, veganYN: isvegan
    }));
  }

  const handleChangeInstruction = (id, detail) => {
    document.getElementById("instruction-name").value = detail;
    setEditIndex(id);
  }

  const handleIngredientClick = () => {

    const newValue = document.getElementById("ingredient-name").value;
    if (newValue) {
      if (editIndex !== -1) {
        console.log('new value ingredient-name', newValue);
        console.log('edit index ', editIndex);

        let x = ingredients.find(x => x.ingredient_id === editIndex).ingredientName = newValue;
        console.log('value has been changed.');
        document.getElementById("ingredient-name").value = '';
        setEditIndex(-1);
      } else {
        let object = {};
        object.ingredient_id = getRandomInt(300);
        object.ingredientName = newValue;

        setIngredients([...ingredients, object]);

        document.getElementById("ingredient-name").value = '';
        setEditIndex(-1);

      }
    }

  }

  const handleChangeIngredient = (id, detail) => {
    document.getElementById("ingredient-name").value = detail;
    setEditIndex(id);
  }

  const clearRecipeItem = () => {

    setRecipe({ recipeId: null, recipeName: '', creationDate: '', veganYN: '',suitableCount:'' });
    setIngredients([]);
    setInstructions([]);
    document.getElementById('showInfoMessage').innerHTML = '';

  }


  const insertRecipeItem = (e) => {

    e.preventDefault();

    if(!recipe.recipeName.trim()){
      alert('recipe name cannot be null!');
      return;
    }

    if(!recipe.suitableCount.trim()){
      alert('suitableCount cannot be null!');
      return;
    }

    if(ingredients.length==0){
      alert('recipe ingredients must have least one item!');
      return;
    }

    if(instructions.length==0){
      alert('recipe instructions must have least one item!');
      return;
    }

    

    if (recipe && ingredients.length > 0 && instructions.length > 0) {
      console.log('before insert');

      ingredients.map(item=>{item.ingredient_id=null});
      instructions.map(item=>{item.instruction_id=null});
      console.log('id removed');

      recipe.recipeIngredientsList = [...ingredients];
      recipe.recipeInstructionModelList = [...instructions];
      if(recipe.veganYN==='' || recipe.veganYN.length<=0){
        recipe.veganYN='N';
      }
      recipe.recipeId = null;
     

      console.log('recipe', recipe);

      const element = document.getElementById('showInfoMessage');
      axios.post('http://localhost:8080/api/v1/recipe/create', recipe,{withCredentials: true})
        .then(response => element.innerHTML = 'operastion has been successful')
        .catch(error => {
          element.innerHTML = `Error: ${error.message}`;
          console.error('There was an error!', error);
          return;
        });

        clearRecipeItem();

    }

  }


  return (

    
    <div className="ui raised very padded text container segment">

      <form className="ui form" onSubmit={insertRecipeItem}>
        <div className="field">
          <label>Recipe Name</label>
          <input type="text" autoComplete='off' name="recipeName" placeholder="Recipe Name" value={recipe.recipeName} onChange={handlerOnChangeInput} />
        </div>
        <div className="field ">
          <label>Creation Date</label>
          <div class="ui  input">
            <input type="date" autoComplete='off' name="creationDate" placeholder="dd-mm-yyyy HH:mm" value={recipe.creationDate} onChange={handlerOnChangeInput} />
          </div>
        </div>
        <div className="field ">
          <label>Number Of Suitable Count</label>
          <div class="ui  input">
            <input type="text" autoComplete='off' name="suitableCount" placeholder="Enter suitable count" value={recipe.suitableCount} onChange={handlerOnChangeInput} />
          </div>
        </div>
        <div className="field">
          <div className="ui checkbox">
            <input type="checkbox" tabindex="0" onChange={onChangeVeganYN} />
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
            {
              instructions.map(item => {

                return (
                  <li id={item.instruction_id} key={item.instruction_id} onClick={() => handleChangeInstruction(item.instruction_id, item.instructionDetail)}>{item.instructionDetail}</li>
                );
              })
            }
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
            {
              ingredients.map(item => {

                return (
                  <li className="item" key={item.ingredient_id} id={item.ingredient_id} onClick={() => handleChangeIngredient(item.ingredient_id, item.ingredientName)}>{item.ingredientName}</li>
                );
              })
            }
          </ul>

        </div>
        <br /><br></br>
        <div className='ui grid'>
          <div className='twelve wide column'></div>
          <input type="button" className="ui right floated yellow button" onClick={clearRecipeItem} value="Clear" />
          <button className="ui right floated primary button" type="submit">Save</button>
        </div>
        <br /><br />
        <div class="ignored ui info message" id="showInfoMessage">

        </div>
      </form>

    </div>
  );
}

export default RecipeInsert;