package com.erpmarcenaria.SistemaGestao.controller;

import com.erpmarcenaria.SistemaGestao.dto.ClienteDTO;
import com.erpmarcenaria.SistemaGestao.dto.Response;
import com.erpmarcenaria.SistemaGestao.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> addCliente(@RequestBody @Valid ClienteDTO clienteDTO) {
        return ResponseEntity.ok(clienteService.addCliente(clienteDTO));
    }

    @GetMapping("/todos")
    public ResponseEntity<Response> getAllClientes() {
        return ResponseEntity.ok(clienteService.getAllClientes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getClienteById(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.getClienteById(id));
    }

    @GetMapping("/{id}/projetos")
    public ResponseEntity<Response> getProjetosByCliente(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.getProjetosByCliente(id));
    }


    @PutMapping("/atualizar/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateCliente(@PathVariable Long id,
                                                  @RequestBody @Valid ClienteDTO clienteDTO) {
        return ResponseEntity.ok(clienteService.updateCliente(id, clienteDTO));
    }

    @DeleteMapping("/deletar/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteCliente(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.deleteCliente(id));
    }
}
