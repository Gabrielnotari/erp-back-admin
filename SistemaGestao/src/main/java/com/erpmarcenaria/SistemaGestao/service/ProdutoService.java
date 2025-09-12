package com.erpmarcenaria.SistemaGestao.service;

import com.erpmarcenaria.SistemaGestao.dto.ProdutoDTO;
import com.erpmarcenaria.SistemaGestao.dto.Response;
import org.springframework.web.multipart.MultipartFile;

public interface ProdutoService {
    Response saveProduto(ProdutoDTO produtoDTO, MultipartFile imageFile);
    Response updateProduto(ProdutoDTO produtoDTO, MultipartFile imageFile);
    Response getAllProduto();
    Response getProdutoById(Long id);
    Response deleteProduto(Long id);
}
