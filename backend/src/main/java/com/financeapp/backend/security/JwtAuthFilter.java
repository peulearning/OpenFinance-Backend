package com.financeapp.backend.security;

import org.springframework.stereotype.Component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

import java.io.IOException;

import org.springframework.security.core.userdetails.UserDetailsService;


@Component
public class JwtAuthFilter extends OncePerRequestFilter {


private final JwtUtil jwtUtil;
private final UserDetailsService userDetailsService;


public JwtAuthFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
this.jwtUtil = jwtUtil;
this.userDetailsService = userDetailsService;
}


  @Override
  protected void doFilterInternal(
  HttpServletRequest request,
  HttpServletResponse response,
  FilterChain filterChain
  ) throws ServletException, IOException {


  final String authHeader = request.getHeader("Authorization");


  if (authHeader != null && authHeader.startsWith("Bearer ")) {
  String token = authHeader.substring(7);
  try {
  String username = jwtUtil.getSubject(token);
  UserDetails ud = userDetailsService.loadUserByUsername(username);


  UsernamePasswordAuthenticationToken auth =
  new UsernamePasswordAuthenticationToken(
  ud,
  null,
  ud.getAuthorities()
  );


  SecurityContextHolder.getContext().setAuthentication(auth);
  } catch (Exception ignored) {}
  }


  filterChain.doFilter(request, response);
  }
}