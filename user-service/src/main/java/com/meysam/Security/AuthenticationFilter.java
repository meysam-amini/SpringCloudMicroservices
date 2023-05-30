package com.meysam.Security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meysam.Model.CustomUserDetails;
import com.meysam.Model.LogInRequestModel;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final Environment environment;

    public AuthenticationFilter(Environment environment,
                                AuthenticationManager authenticationManager) {
        this.environment = environment;
        super.setAuthenticationManager(authenticationManager);
    }


    /*In this method, we have access to HttpServletRequest
    * and HttpServletResponse. After getting credentials,
    * we map them to an object and making a token for user
    *  */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {

            LogInRequestModel creds = new ObjectMapper()
                    .readValue(request.getInputStream(), LogInRequestModel.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getEmail(),
                            creds.getPassword(),
                            new ArrayList<>()
                    )
            );
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
       String publicUserID=((CustomUserDetails)authResult.getPrincipal()).getUserID();

       String token= Jwts.builder()
               .setSubject(publicUserID)
               .setExpiration(new Date(System.currentTimeMillis()+
                       Long.parseLong(environment.getProperty("token.expiration_time"))))
               .signWith(SignatureAlgorithm.HS512, environment.getProperty("token.secret"))
               .compact();

       response.addHeader("token",token);
       response.addHeader("userID",publicUserID);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        System.err.println(failed);


    }
}
