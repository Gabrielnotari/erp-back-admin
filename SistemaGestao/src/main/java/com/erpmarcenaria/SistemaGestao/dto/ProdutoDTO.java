package com.erpmarcenaria.SistemaGestao.dto;

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
public class ProdutoDTO {
    private Long id;

    private Long produtoId;
    private Long categoriaId;
    private Long fornecedorId;

    private String nome;

    private String sku;

    private BigDecimal preco;

    private Integer quantidadeEstoque;

    private String descricao;

    private String imageUrl;

    private LocalDateTime dataExpiracao;

    private  LocalDateTime dataAtualizacao;

    private LocalDateTime dataCriacao;

}
