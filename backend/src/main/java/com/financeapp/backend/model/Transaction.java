package com.financeapp.backend.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "transactions")
public class Transaction {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;


  private String description;

  private BigDecimal amount; // Dinheiro

  


}
