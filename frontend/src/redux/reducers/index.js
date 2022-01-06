import { combineReducers } from "redux";
import { recipeReducer ,selectedRecipeReducer} from "./productReducer";
import { authReducer } from './authReducer';

export const reducers = combineReducers({
    allRecipes : recipeReducer,
    recipe: selectedRecipeReducer,
    auth:authReducer
})
