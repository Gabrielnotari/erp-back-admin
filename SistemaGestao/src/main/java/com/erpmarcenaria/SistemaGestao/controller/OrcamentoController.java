package com.erpmarcenaria.SistemaGestao.controller;

import com.erpmarcenaria.SistemaGestao.dto.Response;
import com.erpmarcenaria.SistemaGestao.dto.SolicitacaoOrcamento;
import com.erpmarcenaria.SistemaGestao.enums.StatusOrcamento;
import com.erpmarcenaria.SistemaGestao.service.OrcamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orcamentos")
@RequiredArgsConstructor
public class OrcamentoController {

    private final OrcamentoService orcamentoService;

    @PostMapping("/compra")
    public ResponseEntity<Response> restockInventory(@RequestBody @Valid SolicitacaoOrcamento solicitacaoOrcamento) {
        return ResponseEntity.ok(orcamentoService.restockInventory(solicitacaoOrcamento));
    }
    @PostMapping("/venda")
    public ResponseEntity<Response> sell(@RequestBody @Valid SolicitacaoOrcamento solicitacaoOrcamento) {
        return ResponseEntity.ok(orcamentoService.sell(solicitacaoOrcamento));
    }
    @PostMapping("/retorno")
    public ResponseEntity<Response> returnToFornecedor(@RequestBody @Valid SolicitacaoOrcamento solicitacaoOrcamento) {
        return ResponseEntity.ok(orcamentoService.returnToFornecedor(solicitacaoOrcamento));
    }

    @GetMapping("/todos")
    public ResponseEntity<Response> getAllOrcamentos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "1000") int size,
            @RequestParam(required = false) String searchText
    ) {
        return ResponseEntity.ok(orcamentoService.getAllOrcamentos(page, size, searchText));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getOrcamentoById(@PathVariable Long id) {
        return ResponseEntity.ok(orcamentoService.getOrcamentoById(id));
    }

    @GetMapping("/by-month-year")
    public ResponseEntity<Response> getAllOrcamentoByMonthAndYear(
            @RequestParam int month,
            @RequestParam int year
    ) {
        return ResponseEntity.ok(orcamentoService.getAllOrcamentoByMonthAndYear(month, year));
    }

    @PutMapping("/atualizar/{orcamentoId}")
    public ResponseEntity<Response> updateOrcamentoStatus(
            @PathVariable Long orcamentoId,
            @RequestBody @Valid StatusOrcamento status) {
        System.out.println("ID IS: " + orcamentoId);
        System.out.println("Status IS: " + status);
        return ResponseEntity.ok(orcamentoService.updateOrcamentoStatus(orcamentoId, status));
    }
}
