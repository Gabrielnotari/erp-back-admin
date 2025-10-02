package com.erpmarcenaria.SistemaGestao.service;

import com.erpmarcenaria.SistemaGestao.dto.ClienteDTO;
import com.erpmarcenaria.SistemaGestao.dto.Response;

public interface ClienteService {
    Response addCliente(ClienteDTO clienteDTO);

    Response updateCliente(Long id, ClienteDTO clienteDTO);

    Response getAllClientes();

    Response getClienteById(Long id);

    Response deleteCliente(Long id);

    Response getProjetosByCliente(Long clienteId);

}
