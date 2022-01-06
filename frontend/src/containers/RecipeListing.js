import React,{useEffect} from 'react';
import { useDispatch, useSelector } from 'react-redux';
import RecipeComponent from './RecipeComponent';
import axios from 'axios';
import { setRecipes } from '../redux/actions/recipeActions';
import {authToken} from './utils/authToken';
import { useNavigate} from 'react-router-dom';


const RecipeListing = ()=>{

    if (localStorage.jwtToken) {
        console.log('jwt token',localStorage.jwtToken);
        authToken(localStorage.jwtToken);
    }

    const recipes  = useSelector( (state)=>state);
    const dispatch = useDispatch();
    const navigate = useNavigate();


    const fetchAllRecipes = async ()=>{
        const headers = {
            'Content-Type': 'application/x-www-form-urlencoded'
        };
        const response = await axios.get("http://localhost:8080/api/v1/recipe/allrecipes",
        {headers},{withCredentials: true}).catch( (err)=>{
            console.log("Err",err);
            navigate('/');
        });
        console.log("Recipes",response.data);
        dispatch(setRecipes(response.data));
    };

    useEffect(()=>{
        if (localStorage.jwtToken) {
            fetchAllRecipes();
        }else{
            navigate('/');
        }
       
    },[]);

    console.log('After fetch recipes',recipes);


        return(
            <div className="ui grid container">
                <RecipeComponent/>
            </div>
        );
};

export default RecipeListing;