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
}

