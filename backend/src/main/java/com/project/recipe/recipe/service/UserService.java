package com.project.recipe.recipe.service;


import com.project.recipe.recipe.entity.user.UserInfo;

public interface UserService {

    public UserInfo register(UserInfo userInfo);

    String authenticate(UserInfo user);
}
