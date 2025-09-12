package com.erpmarcenaria.SistemaGestao.controller;

import com.erpmarcenaria.SistemaGestao.dto.FornecedorDTO;
import com.erpmarcenaria.SistemaGestao.dto.Response;
import com.erpmarcenaria.SistemaGestao.service.FornecedorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fornecedores")
@RequiredArgsConstructor
public class FornecedorController {

    private final FornecedorService fornecedorService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> addSupplier(@RequestBody @Valid FornecedorDTO fornecedorDTO) {
        return ResponseEntity.ok(fornecedorService.addFornecedor(fornecedorDTO));
    }

    @GetMapping("/todos")
    public ResponseEntity<Response> getAllFornecedores() {
        return ResponseEntity.ok(fornecedorService.getAllFornecedores());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Response> getFornecedorById(@PathVariable Long id) {
        return ResponseEntity.ok(fornecedorService.getFornecedorById(id));
    }

    @PutMapping("/atualizar/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateFornecedor(@PathVariable Long id, @RequestBody @Valid FornecedorDTO fornecedorDTO) {
        return ResponseEntity.ok(fornecedorService.updateFornecedor(id, fornecedorDTO));
    }

    @DeleteMapping("/deletar/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteFornecedor(@PathVariable Long id) {
        return ResponseEntity.ok(fornecedorService.deleteFornecedor(id));
    }
}
