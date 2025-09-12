package com.erpmarcenaria.SistemaGestao.dto;

import com.erpmarcenaria.SistemaGestao.enums.StatusOrcamento;
import com.erpmarcenaria.SistemaGestao.enums.TipoOrcamento;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrcamentoDTO {
    private Long id;

    private Integer totalProdutos;

    private BigDecimal precoTotal;


    private TipoOrcamento tipoOrcamento;

    private StatusOrcamento status;

    private String descricao;

    private LocalDateTime dataAtualizacao;

    private LocalDateTime dataCriacao;

    private UserDTO user;

    private ProdutoDTO produto;

    private FornecedorDTO fornecedor;
}
