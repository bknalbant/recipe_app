import React, { useEffect, useState, } from 'react';
import { useDispatch } from "react-redux";
import { useNavigate} from 'react-router-dom';
import {authenticateUser} from '../redux/actions/authActions';
import axios from 'axios';
import { getCookie } from './utils/authToken';


const RecipeLogin = () => {

    const [error, setError] = useState();
    const [show, setShow] = useState(false);

    const initialState = {
        username: "",
        password: "",
    };

    const navigate = useNavigate();

    const [user, setUser] = useState(initialState);

    const fetchCsrf = async ()=>{
      const response = await axios.get("http://localhost:8080/recipe/v1/users/default",{withCredentials: true}).catch( (err)=>{
            console.log("Err",err);
        });
        const csrfToken=getCookie('XSRF-TOKEN');
        axios.defaults.headers.common["X-XSRF-TOKEN"] = csrfToken;
    }

    useEffect(() => {
        fetchCsrf();
        if (localStorage.jwtToken){
         navigate("/home");   
        }
    },[])

    

    const credentialChange = (event) => {
        const { name, value } = event.target;
        setUser({ ...user, [name]: value });
      };

      const dispatch = useDispatch();

      const validateUser = () => {
        dispatch(authenticateUser(user.username, user.password))
          .then((response) => {
            console.log(response.data);
            return navigate("/home");
          })
          .catch((error) => {
            console.log(error.message);
            setShow(true);
            resetLoginForm();
            setError("Invalid username and password");
          });
      };
    
      const resetLoginForm = () => {
        setUser(initialState);
      };

    return(
        <div className="ui middle aligned center aligned grid">
  <div className="column five wide">
    <h2 className="ui teal image header">
      <div className="content">
        Log-in to your account
      </div>
    </h2>
    <form className="ui large form">
      <div className="ui stacked segment">
        <div className="field">
          <div className="ui left icon input">
            <i className="user icon"></i>
            <input type="text" autoComplete='off' name="username" placeholder="User Name" value={user.username} onChange={credentialChange}/>
          </div>
        </div>
        <div className="field">
          <div className="ui left icon input">
            <i className="lock icon"></i>
            <input type="password" name="password" placeholder="Password" value={user.password} onChange={credentialChange}/>
          </div>
        </div>
        <input type="button" className="ui fluid large teal submit button" value="Login" onClick={validateUser} disabled={user.username.length === 0 || user.password.length === 0}/>
      </div>

      <div className="ui error message"></div>
      {
        show ? error:''
      }

    </form>
  </div>
</div>
    );

}

export default RecipeLogin;



