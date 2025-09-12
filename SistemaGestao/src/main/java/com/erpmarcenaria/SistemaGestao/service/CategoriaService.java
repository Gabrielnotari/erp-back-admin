package com.erpmarcenaria.SistemaGestao.service;

import com.erpmarcenaria.SistemaGestao.dto.CategoriaDTO;
import com.erpmarcenaria.SistemaGestao.dto.Response;

public interface CategoriaService {
    Response createCategory(CategoriaDTO categoriaDTO);
    Response getAllCategories();
    Response getCategoryById(Long id);
    Response updateCategory(Long id, CategoriaDTO categoriaDTO);
    Response deleteCategory(Long id);
}
