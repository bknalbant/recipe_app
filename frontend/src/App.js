import './App.css';
import Header from './containers/Header';
import {BrowserRouter as Router,Routes,Route} from 'react-router-dom';
import RecipeListing from './containers/RecipeListing';
import RecipeDetail from './containers/RecipeDetail';
import RecipeInsert from './containers/RecipeInsert';
import RecipeLogin from './containers/RecipeLogin';

function App() {
  return (
    <div className="App">
      
        <Router>
            <Header/>
            <Routes>
              <Route path="/" element={<RecipeLogin/>}/>
              <Route path="/home"  element={<RecipeListing/>}/>
              <Route path="/recipe/:recipeId"  element={<RecipeDetail/>}/>
              <Route path="/addrecipe" element={<RecipeInsert/>}/>
              <Route>404 Not Found</Route>
            </Routes>
          
        </Router>
  
    </div>
  );
}

export default App;
