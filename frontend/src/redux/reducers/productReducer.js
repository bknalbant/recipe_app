import { ActionTypes } from "../constant/action-types";

const initialState={
    recipes : []
}

export const recipeReducer = (state=initialState,{type,payload}) =>{

        switch (type) {
            case ActionTypes.SET_RECIPES:
                return {...state , recipes:payload};
            default:
                return state;
        }

}

export const selectedRecipeReducer = (state = {}, { type, payload }) => {
    console.log(type);
    switch (type) {
      case ActionTypes.SELECTED_RECIPE:
        return { ...state, ...payload };
      case ActionTypes.REMOVE_SELECTED_RECIPE:
        return {};
      default:
        return state;
    }
  };