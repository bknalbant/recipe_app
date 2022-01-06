package com.project.recipe.recipe.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.recipe.recipe.entity.user.MyUserDetails;
import com.project.recipe.recipe.entity.user.UserInfo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.project.recipe.recipe.utils.Constants.*;

public class UserAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;


    public UserAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl(LOGIN_PAGE);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException
    {
        try
        {
            UserInfo userData = new ObjectMapper().readValue(request.getInputStream(), UserInfo.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userData.getUsername(), userData.getPassword(),new ArrayList<>()));
        }
        catch(IOException e)
        {
            throw new RuntimeException("Could not read request" + e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Authentication authentication)
    {
        MyUserDetails userDetails=((MyUserDetails) authentication.getPrincipal());

        String token = Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, HEADER_SecretKeyToGenJWTs.getBytes())
                .claim(ROLES,userDetails.getAuthorities())
                .compact();
        response.addHeader(HEADER_AUTHORIZATION,HEADER_BEARER+" "+ token);
    }




}
