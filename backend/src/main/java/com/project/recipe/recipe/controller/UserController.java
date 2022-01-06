package com.project.recipe.recipe.controller;

import com.project.recipe.recipe.entity.user.UserInfo;
import com.project.recipe.recipe.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/recipe/v1/users")
public class UserController {


    private final UserService userService;

    @ApiOperation("signUp()-> performs to sign up in recipe system")
    @PostMapping("/signup")
    public ResponseEntity<UserInfo> signUp(@Valid @RequestBody UserInfo user){
        return new ResponseEntity<>(userService.register(user), HttpStatus.OK);
    }


    @ApiOperation("authenticate()-> performs to log in recipe system")
    @PostMapping(value = "/authenticate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> authenticate(@RequestBody UserInfo user) {
        return new ResponseEntity<>(userService.authenticate(user), HttpStatus.OK);

    }
    @ApiOperation("default request()-> performs to default request in recipe system")
    @GetMapping(value="/default")
    public ResponseEntity<String> fetchCsrf(){
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
