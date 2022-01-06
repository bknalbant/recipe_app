package com.project.recipe.recipe.exception.handler;

import com.project.recipe.recipe.exception.RecipeAlreadyExistException;
import com.project.recipe.recipe.exception.RecipeNotFoundException;
import com.project.recipe.recipe.exception.UserAlreadyExistException;
import com.project.recipe.recipe.exception.UserNameNotFoundException;
import com.project.recipe.recipe.response.ErrorResponse;
import com.project.recipe.recipe.utils.Constants;
import com.project.recipe.recipe.utils.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GenericExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorResponse> handleError(final Exception ex) {

        ErrorResponse errorResponse = null;

        if (ex.getCause() instanceof RecipeAlreadyExistException || ex instanceof RecipeAlreadyExistException) {
            log.error("Recipe Already Exist  Exception!", ex);
            errorResponse = ErrorResponse.builder().returnCode(Constants.Return.RECIPE_ALREADY_EXIST.getCode())
                    .returnMessage(Constants.Return.FAIL.getMessage()).details(Constants.Return.RECIPE_ALREADY_EXIST.getMessage()).build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
        else if (ex.getCause() instanceof RecipeNotFoundException || ex instanceof RecipeNotFoundException) {
            log.error("Recipe could not be founded !", ex);
            errorResponse = ErrorResponse.builder().returnCode(Constants.Return.RECIPE_NOT_FOUND_EXCEPTION.getCode())
                    .returnMessage(Constants.Return.FAIL.getMessage()).details(Constants.Return.RECIPE_NOT_FOUND_EXCEPTION.getMessage()).build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
        else if (ex.getCause() instanceof UserAlreadyExistException || ex instanceof UserAlreadyExistException) {
            log.error("User Already Exist  Exception!", ex);
            errorResponse = ErrorResponse.builder().returnCode(Constants.Return.USER_ALREADY_EXIST.getCode())
                    .returnMessage(Constants.Return.FAIL.getMessage()).details(Constants.Return.USER_ALREADY_EXIST.getMessage()).build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
        else if (ex.getCause() instanceof UserNameNotFoundException || ex instanceof UserNameNotFoundException) {
            log.error("User name could not be founded!", ex);
            errorResponse = ErrorResponse.builder().returnCode(Constants.Return.USERNAME_NOT_FOUND_EXCEPTION.getCode())
                    .returnMessage(Constants.Return.FAIL.getMessage()).details(Constants.Return.USERNAME_NOT_FOUND_EXCEPTION.getMessage()).build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }else {
            log.error("Generic Exception!", ex);
            errorResponse = ErrorResponse.builder().returnCode(Constants.Return.FAIL.getCode())
                    .returnMessage(Constants.Return.FAIL.getMessage())
                    .details(ExceptionUtil.getExceptionTrace(ex)).
                     build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }



    }



}
