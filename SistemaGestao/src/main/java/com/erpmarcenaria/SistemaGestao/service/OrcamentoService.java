package com.erpmarcenaria.SistemaGestao.service;

import com.erpmarcenaria.SistemaGestao.dto.Response;
import com.erpmarcenaria.SistemaGestao.dto.SolicitacaoOrcamento;
import com.erpmarcenaria.SistemaGestao.enums.StatusOrcamento;

public interface OrcamentoService {
    Response restockInventory(SolicitacaoOrcamento solicitacaoOrcamento);
    Response sell(SolicitacaoOrcamento solicitacaoOrcamento);
    Response returnToFornecedor(SolicitacaoOrcamento solicitacaoOrcamento);
    Response getAllOrcamentos(int page, int size, String searchText);
    Response getOrcamentoById(Long id);
    Response getAllOrcamentoByMonthAndYear(int month, int year);
    Response updateOrcamentoStatus(Long orcamentoId, StatusOrcamento statusOrcamento);
}
