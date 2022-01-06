import React from 'react';
import { Link } from 'react-router-dom';

const Header = () => {

    return (
        <div class="ui attached stackable menu">
            <div class="ui container">
                <Link to={`/home`}>
                <a class="item">
                    <i class="home icon"></i> Home
                </a>
                </Link>
                <Link to={`/addrecipe`}>
                <a class="item">
                    <i class="grid layout icon"></i> Add Recipe
                </a>
                </Link>
            </div>
        </div>
    )
};

export default Header;