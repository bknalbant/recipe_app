package com.project.recipe.recipe.utils;

import com.project.recipe.recipe.entity.user.MyUserDetails;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

import static com.project.recipe.recipe.utils.Constants.*;

@Slf4j
public class JwtBuilder {


    public static String jwtTokenBuild(MyUserDetails userDetails){
        String token = Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, HEADER_SecretKeyToGenJWTs.getBytes())
                .claim(ROLES,userDetails.getAuthorities())
                .compact();
        log.info("[JwtBuilder jwtTokenBuild()] -> token has been created "+token);
        return token;
    }
}
