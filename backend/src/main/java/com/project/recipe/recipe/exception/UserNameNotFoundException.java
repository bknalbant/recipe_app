package com.project.recipe.recipe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.OK, reason = "User Name could not be found!")
public class UserNameNotFoundException extends RuntimeException{
}
