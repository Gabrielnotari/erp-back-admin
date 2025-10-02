package com.erpmarcenaria.SistemaGestao.dto;

import com.erpmarcenaria.SistemaGestao.enums.StatusProjeto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjetoDTO {

    private Long id;
    private String nomeProjeto;
    private String descricao;
    private StatusProjeto status;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate previsaoEntrega;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataInicio;

    private BigDecimal valor;

    private Long clienteId;
    private String clienteNome;
}
