package com.erpmarcenaria.SistemaGestao.dto;

import com.erpmarcenaria.SistemaGestao.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    //generico
    private int status;
    private String message;
    //para login
    private String token;
    private UserRole role;
    private String expirationTime;

    //para paginação
    private Integer totalPages;
    private Long totalElements;

    //opcional
    private UserDTO user;
    private List<UserDTO> users;

    private FornecedorDTO fornecedor;
    private List<FornecedorDTO> fornecedores;

    private CategoriaDTO categoria;
    private List<CategoriaDTO> categorias;

    private ProdutoDTO produto;
    private List<ProdutoDTO> produtos;

    private OrcamentoDTO orcamento;
    private List<OrcamentoDTO> orcamentos;

    private List<ProjetoDTO> projetos;
    private ProjetoDTO projeto;

    private ClienteDTO cliente;           // para getClienteById
    private List<ClienteDTO> clientes;

    private final LocalDateTime timestamp = LocalDateTime.now();
}
