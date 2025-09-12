package com.erpmarcenaria.SistemaGestao.dto;

import com.erpmarcenaria.SistemaGestao.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {
    private Long id;

    private String nome;

    private String email;

    @JsonIgnore
    private String senha;

    private String numeroTelefone;

    private UserRole role;

    private List<OrcamentoDTO> orcamentos;

    private LocalDateTime dataCriacao;
}
