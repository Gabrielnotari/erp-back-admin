package com.erpmarcenaria.SistemaGestao.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SolicitacaoOrcamento {
    @Positive(message = "Produto é obrigatório")
    private Long produtoId;

    @Positive(message = "Quantidade id é obrigatório")
    private Integer quantidade;

    private Long fornecedorId;

    private String descricao;
}
