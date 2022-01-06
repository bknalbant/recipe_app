import { ActionTypes } from "../constant/action-types";
import axios from 'axios';

const AUTH_URL = "http://localhost:8080/recipe/v1/users/authenticate";

export const authenticateUser = (username, password) => async (dispatch) => {
  dispatch(loginRequest());
  try {
    const response = await axios.post(AUTH_URL, {
      username: username,
      password: password,
    },{withCredentials:true});
    console.log('response',response.data);
    localStorage.setItem("jwtToken", response.data);
    dispatch(success({ username: response.data.name, isLoggedIn: true }));
    return Promise.resolve(response.data);
  } catch (error) {
    dispatch(failure());
    return Promise.reject(error);
  }
};

export const logoutUser = () => {
    return (dispatch) => {
      dispatch(logoutRequest());
      localStorage.removeItem("jwtToken");
      dispatch(success({ username: "", isLoggedIn: false }));
    };
  };
  
  const loginRequest = () => {
    return {
      type: ActionTypes.LOGIN_REQUEST,
    };
  };
  
  const logoutRequest = () => {
    return {
      type: ActionTypes.LOGOUT_REQUEST,
    };
  };
  
  const success = (isLoggedIn) => {
    return {
      type: ActionTypes.SUCCESS,
      payload: isLoggedIn,
    };
  };
  
  const failure = () => {
    return {
      type: ActionTypes.FAILURE,
      payload: false,
    };
  };