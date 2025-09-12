package com.erpmarcenaria.SistemaGestao.controller;

import com.erpmarcenaria.SistemaGestao.dto.Response;
import com.erpmarcenaria.SistemaGestao.dto.SolicitacaoCadastro;
import com.erpmarcenaria.SistemaGestao.dto.SolicitacaoLogin;
import com.erpmarcenaria.SistemaGestao.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Response> registerUser(@RequestBody @Valid SolicitacaoCadastro solicitacaoCadastro){
        return ResponseEntity.ok(userService.registerUser(solicitacaoCadastro));
    }

    @PostMapping("/login")
    public ResponseEntity<Response> loginUser(@RequestBody @Valid SolicitacaoLogin solicitacaoLogin){
        return ResponseEntity.ok(userService.loginUser(solicitacaoLogin));
    }
}
