package com.erpmarcenaria.SistemaGestao.controller;


import com.erpmarcenaria.SistemaGestao.dto.ProjetoDTO;
import com.erpmarcenaria.SistemaGestao.dto.Response;
import com.erpmarcenaria.SistemaGestao.service.ProjetoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/projetos")
@RequiredArgsConstructor
public class ProjetoController {

    private final ProjetoService projetoService;

    // Adicionar novo projeto
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> addProjeto(@RequestBody @Valid ProjetoDTO projetoDTO) {
        return ResponseEntity.ok(projetoService.addProjeto(projetoDTO));
    }

    // Buscar todos os projetos
    @GetMapping("/todos")
    public ResponseEntity<Response> getAllProjetos() {
        return ResponseEntity.ok(projetoService.getAllProjetos());
    }

    // Buscar projeto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Response> getProjetoById(@PathVariable Long id) {
        return ResponseEntity.ok(projetoService.getProjetoById(id));
    }

    // Atualizar projeto
    @PutMapping("/atualizar/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateProjeto(@PathVariable Long id,
                                                  @RequestBody @Valid ProjetoDTO projetoDTO) {
        return ResponseEntity.ok(projetoService.updateProjeto(id, projetoDTO));
    }

    // Deletar projeto
    @DeleteMapping("/deletar/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteProjeto(@PathVariable Long id) {
        return ResponseEntity.ok(projetoService.deleteProjeto(id));
    }
}


