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
    //generic
    private int status;
    private String message;
    //for login
    private String token;
    private UserRole role;
    private String expirationTime;

    //for pagination
    private Integer totalPages;
    private Long totalElements;

    //data output optional
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

    private final LocalDateTime timestamp = LocalDateTime.now();
}
