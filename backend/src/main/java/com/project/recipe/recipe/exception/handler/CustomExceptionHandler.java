package com.project.recipe.recipe.exception.handler;

import com.project.recipe.recipe.response.ValidatorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<?> methodArgumentNotValidException(org.springframework.web.bind.MethodArgumentNotValidException ex)
    {
        log.error("Generic Exception", ex);
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        String errorMessage = fieldErrors.get(0).getDefaultMessage();
        return ResponseEntity.badRequest().body(new ValidatorResponse(errorMessage));
    }

}
