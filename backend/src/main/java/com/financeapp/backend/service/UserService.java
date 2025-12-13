package com.financeapp.backend.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.financeapp.backend.model.User;
import com.financeapp.backend.repository.UserRepository;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();



  public UserService (UserRepository userRepository){
    this.userRepository = userRepository;
  }

  public User register(String name, String email, String rawPassword){
    userRepository.findByEmail(email).ifPresent(u -> {
      throw new IllegalArgumentException("Email already in use");
    });

    User u = new User();
    u.setName(name);
    u.setEmail(email);
    u.setPasswordHash(encoder.encode(rawPassword));


    return userRepository.save(u);

  }

  public User findByEmail(String emial){
    return userRepository.findByEmail(emial).orElseThrow(() -> new IllegalArgumentException("User not found"));
  }

  public boolean checkPassword(User user, String rawPassword){
    return encoder.matches(rawPassword, user.getPasswordHash());
  }

}
