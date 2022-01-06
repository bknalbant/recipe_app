package com.project.recipe.recipe.service.impl;

import com.project.recipe.recipe.entity.user.MyUserDetails;
import com.project.recipe.recipe.entity.user.UserInfo;
import com.project.recipe.recipe.exception.UserNameNotFoundException;
import com.project.recipe.recipe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {


    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)  {
        log.info("[UserDetailServiceImpl loadUserByUsername()] has been called with this username "+username);
        UserInfo userInfo = userRepository.findByUsername(username).orElseThrow(UserNameNotFoundException::new);
        log.info("[UserDetailServiceImpl loadUserByUsername()]-> -> user has been found!");
        return new MyUserDetails(userInfo);
    }

}
