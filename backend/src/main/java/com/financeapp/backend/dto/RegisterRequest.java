package com.financeapp.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

  @NotBlank
  private String name;

  @NotBlank
  private String email;

  @NotBlank
  @Size(min = 6, message = "Password must be at least 6 characters long")
  private String password;

  public String getPassword() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getPassword'");
  }

  public String getName() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getName'");
  }

  public String getEmail() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getEmail'");
  }
}

