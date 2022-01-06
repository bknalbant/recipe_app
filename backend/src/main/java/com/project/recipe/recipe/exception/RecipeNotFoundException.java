package com.project.recipe.recipe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.OK, reason = "Recipe Not found!")
public class RecipeNotFoundException extends RuntimeException{

}
