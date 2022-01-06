package com.project.recipe.recipe.repository;

import com.project.recipe.recipe.entity.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserInfo,Long> {

    public Optional<UserInfo> findByUsername(@Param("username") String username);

}
