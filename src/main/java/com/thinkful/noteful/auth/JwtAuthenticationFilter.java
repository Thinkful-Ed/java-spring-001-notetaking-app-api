package com.thinkful.noteful.auth;

import static com.thinkful.noteful.auth.SecurityConstants.EXPIRATION_TIME;
import static com.thinkful.noteful.auth.SecurityConstants.HEADER_NAME;
import static com.thinkful.noteful.auth.SecurityConstants.JWT_SECRET;
import static com.thinkful.noteful.auth.SecurityConstants.TOKEN_PREFIX;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thinkful.noteful.users.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private AuthenticationManager authenticationManager;

  public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
    super.setAuthenticationFailureHandler(new JwtAuthenticationFailureHandler());
    setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/login"));
  }

  @Override
  public Authentication attemptAuthentication(
        HttpServletRequest req, 
        HttpServletResponse res) throws AuthenticationException {
    try {
      User credentials = new ObjectMapper()
            .readValue(req.getInputStream(), User.class);
      return authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                  credentials.getUsername(), 
                  credentials.getPassword(),
                  new ArrayList<>())
      );
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void successfulAuthentication(
        HttpServletRequest req,
        HttpServletResponse res,
        FilterChain chain,
        Authentication auth
  ) throws IOException, ServletException {
    String token = Jwts.builder()
          .setSubject(((org.springframework.security.core.userdetails.User)auth
                .getPrincipal()).getUsername())
          .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
          .signWith(SignatureAlgorithm.HS512, JWT_SECRET.getBytes())
          .compact();
    res.addHeader(HEADER_NAME, TOKEN_PREFIX + token); 
    res.addHeader("Content-Type", "application/json"); 
    res.getWriter().printf("{\"authToken\": \"%s\"}", token);    
  }
}