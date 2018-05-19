package com.thinkful.noteful;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class NoteExceptionHandler extends ResponseEntityExceptionHandler {

  
  @ExceptionHandler(value = { NoteException.class })
  protected ResponseEntity<Object> handleError(NoteException ex, WebRequest req) {
    
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
    return handleExceptionInternal(
          ex, 
          ex.toString(),
          headers, 
          ex.getStatus(), 
          req);
  }
}