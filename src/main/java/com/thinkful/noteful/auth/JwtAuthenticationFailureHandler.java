package com.thinkful.noteful.auth;

import com.thinkful.noteful.NoteException;
import com.thinkful.noteful.NoteStatus;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class JwtAuthenticationFailureHandler implements AuthenticationFailureHandler {

  @Override
  public void onAuthenticationFailure(
        HttpServletRequest request, 
        HttpServletResponse response, 
        AuthenticationException exception)
          throws IOException, ServletException {
    response.setStatus(401);
    response.setContentType("application/json"); 
    response.getWriter().append(new NoteException(
          "",
          "",
          "/api/login",
          "You are not authorized to access this resource.",
          HttpStatus.UNAUTHORIZED,
          NoteStatus.AUTHENTICATION_FAILURE
      ).toString());
  }
}