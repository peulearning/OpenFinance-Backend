package com.financeapp.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping; // <--- IMPORTANTE
import org.springframework.web.bind.annotation.RestController;
import com.financeapp.backend.dto.AuthRequest;
import com.financeapp.backend.dto.AuthResponse;
import com.financeapp.backend.dto.RegisterRequest;
import com.financeapp.backend.model.User;
import com.financeapp.backend.security.JwtUtil;
import com.financeapp.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth") // <--- ADICIONE ISSO AQUI
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil){
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register") // Adicionei a barra / para padronizar
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest req) {
        User u = userService.register(req.getName(), req.getEmail(), req.getPassword());
        return ResponseEntity.ok(new AuthResponse(jwtUtil.generateToken(u.getEmail())));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest req) {
        User u = userService.findByEmail(req.getEmail());

        if (u == null || !userService.checkPassword(u, req.getPassword())) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
        return ResponseEntity.ok(new AuthResponse(jwtUtil.generateToken(u.getEmail())));
    }
}