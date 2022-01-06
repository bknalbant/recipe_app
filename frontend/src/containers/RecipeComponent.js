import React from 'react';
import { useSelector } from 'react-redux';
import { Link } from 'react-router-dom';



const RecipeComponent = () => {

  const recipes = useSelector((state) => state.allRecipes.recipes);

  const renderList = recipes.map((recipe) => {
    const { recipeId, recipeName, creationDate, veganYN , suitableCount,recipeIngredientsList,recipeInstructionModelList } = recipe;
    return (
          <div className="four wide column" key={recipeId} style={{marginTop:20+'px'}}>
          <div className="ui card" >
            <div className="content">
              <div className="header">{recipeName}</div>
            </div>
            <div className="content">
              <h4 className="ui sub header">Details</h4>
              <div className="ui small feed">
                <div className="event">
                  <div className="content">
                    <div className="summary">
                       Is Vegan : { veganYN ==='Y'?'Yes':'No' }
                    </div>
                  </div>
                </div>
                <div className="event">
                  <div className="content">
                    <div className="summary">
                       Number of suitable count : {suitableCount}
                    </div>
                  </div>
                </div>
                <div className="event">
                  <div className="content">
                    <div className="summary">
                       Creation Date : {creationDate}
                    </div>
                  </div>
                </div>
                <div className="event">
                  <div className="content">
                    <div className="summary">
                      Ingredients : 
                      <p>
                          <ul>
                            {recipeIngredientsList.map((ingredient)=>{
                              const {ingredient_id,ingredientName} = ingredient;
                                return(
                                  <li key={ingredient_id}>{ingredientName}</li>
                                );
                            })}
                          </ul>
                      </p>
                    </div>
                  </div>
                </div>
                <div className="event">
                  <div className="content">
                    <div className="summary">
                      Instructions : 
                      <p>
                          <ul>
                            {recipeInstructionModelList.map((instruction)=>{
                              const {instruction_id,instructionDetail} = instruction;
                                return(
                                  <li key={instruction_id}>{instructionDetail}</li>
                                );
                            })}
                          </ul>
                      </p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div className="extra content">
            <Link to={`/recipe/${recipeId}`}>
              <button className="ui button">
                Detail
              </button>
              </Link>
            </div>
          </div>
          </div>
    );
  });

  return <>{renderList}</>;


};

export default RecipeComponent;