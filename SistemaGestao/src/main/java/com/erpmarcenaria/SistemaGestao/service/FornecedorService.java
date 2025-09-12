package com.erpmarcenaria.SistemaGestao.service;

import com.erpmarcenaria.SistemaGestao.dto.FornecedorDTO;
import com.erpmarcenaria.SistemaGestao.dto.Response;

public interface FornecedorService {
    Response addFornecedor(FornecedorDTO fornecedorDTO);
    Response updateFornecedor(Long id, FornecedorDTO fornecedorDTO);
    Response getAllFornecedores();
    Response getFornecedorById(Long id);
    Response deleteFornecedor(Long id);
}
