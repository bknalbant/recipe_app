package com.project.recipe.recipe.service.impl;

import com.project.recipe.recipe.entity.user.MyUserDetails;
import com.project.recipe.recipe.entity.user.UserInfo;
import com.project.recipe.recipe.exception.UserAlreadyExistException;
import com.project.recipe.recipe.repository.UserRepository;
import com.project.recipe.recipe.service.UserService;
import com.project.recipe.recipe.utils.Constants;
import com.project.recipe.recipe.utils.JwtBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private  final AuthenticationManager authenticationManager;


    @Override
    public UserInfo register(UserInfo userInfo) {
        log.info("[UserServiceImpl register()] -> has been called with this username "+userInfo.getUsername());
        if(checkIfUserExist(userInfo.getUsername())){
            log.info("[UserServiceImpl register()] -> user name is already existed!");
            throw new UserAlreadyExistException();
        }
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userRepository.save(userInfo);
        log.info("[UserServiceImpl register()] -> user has been saved recipe system.");
        return userInfo;

    }

    @Override
    public String authenticate(UserInfo user) {
        log.info("[UserServiceImpl authenticate()] -> has been called with this username "+user.getUsername());
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), new ArrayList<>()));
        log.info("[UserServiceImpl authenticate()] -> has been authenticated with this username "+user.getUsername());
        if(authenticate!=null && authenticate.isAuthenticated()){

                 MyUserDetails userDetails=((MyUserDetails) authenticate.getPrincipal());

                 return JwtBuilder.jwtTokenBuild(userDetails);
        }
        return Constants.EMPTY_STRING;

    }


    private boolean checkIfUserExist(String username) {

        ExampleMatcher matcher=ExampleMatcher.
                                matching().withIgnorePaths("id").
                                withMatcher("username",ignoreCase());
        UserInfo entity=new UserInfo();
        entity.setUsername(username);
        Example<UserInfo> ofUser = Example.of(entity, matcher);
        return userRepository.exists(ofUser);
    }
}
