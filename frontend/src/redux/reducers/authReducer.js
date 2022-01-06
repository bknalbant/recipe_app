import { ActionTypes } from "../constant/action-types";

const initialState = {
  username: "",
  isLoggedIn: "",
};

export const authReducer = (state = initialState, action) => {
  switch (action.type) {
    case ActionTypes.LOGIN_REQUEST:
    case ActionTypes.LOGOUT_REQUEST:
      return {
        ...state,
      };
    case ActionTypes.SUCCESS:
    case ActionTypes.FAILURE:
      return {
        username: action.payload.username,
        isLoggedIn: action.payload.isLoggedIn,
      };
    default:
      return state;
  }
};
