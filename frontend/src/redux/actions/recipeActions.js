import { ActionTypes } from "../constant/action-types";

export const setRecipes =(recipes)=>{

        return{
            type : ActionTypes.SET_RECIPES,
            payload : recipes
        };
};     

export const selectedRecipe = (recipe) => {
    return {
      type: ActionTypes.SELECTED_RECIPE,
      payload: recipe,
    };
  };

  export const removeSelectedRecipe = () => {
    return {
      type: ActionTypes.REMOVE_SELECTED_RECIPE
    };
  };