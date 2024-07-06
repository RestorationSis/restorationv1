package com.restorationservice.restorationv1.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restorationservice.restorationv1.security.AuthResponse;
import com.restorationservice.restorationv1.security.AuthService;
import com.restorationservice.restorationv1.security.LoginRequest;
import com.restorationservice.restorationv1.security.RegisterRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping(value = "login")
  public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
    return ResponseEntity.ok(authService.login(request));
  }

  @PostMapping(value = "register")
  public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
    return ResponseEntity.ok(authService.register(request));
  }

  @GetMapping("/test")
  public ResponseEntity<String> testEndpoint() {
    return ResponseEntity.ok("CORS is working!");
  }


  @PostMapping("/posttest")
  public ResponseEntity<String> testPostEndpoint(@RequestBody Map<String, String> payload) {
    return ResponseEntity.ok("POST request success! Received: " + payload);
  }
}