package com.erpmarcenaria.SistemaGestao.service;

import com.erpmarcenaria.SistemaGestao.dto.ProjetoDTO;
import com.erpmarcenaria.SistemaGestao.dto.Response;

public interface ProjetoService {
    Response addProjeto(ProjetoDTO projetoDTO);

    Response updateProjeto(Long id, ProjetoDTO projetoDTO);

    Response getAllProjetos();

    Response getProjetoById(Long id);

    Response deleteProjeto(Long id);
}
