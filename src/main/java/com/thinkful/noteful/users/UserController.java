package com.thinkful.noteful.users;

import com.thinkful.noteful.NoteException;
import com.thinkful.noteful.NoteStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
   
  @Autowired
  private UserRepository userRepository;
  
  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @PostMapping("/")
  public User signup(@RequestBody User user) throws NoteException {
    if (user.getPassword() == null) {
      throw new NoteException(
        "User",
        "password",
        null,
        "missing password in request body",
        HttpStatus.UNPROCESSABLE_ENTITY,
        NoteStatus.VALIDATION_ERROR
      );
    }

    if (user.getUsername() == null) {
      throw new NoteException(
        "User",
        "username",
        null,
        "missing username in request body",
        HttpStatus.UNPROCESSABLE_ENTITY,
        NoteStatus.VALIDATION_ERROR
      );
    }

    if (user.getPassword().trim().length() < 8) {
      throw new NoteException(
        "User",
        "password",
        user.getPassword(),
        "password must be at least 8 characters long",
        HttpStatus.UNPROCESSABLE_ENTITY,
        NoteStatus.VALIDATION_ERROR
      );
    }
    
    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
  }

}