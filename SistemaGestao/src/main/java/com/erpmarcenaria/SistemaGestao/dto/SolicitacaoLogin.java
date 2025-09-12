package com.erpmarcenaria.SistemaGestao.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SolicitacaoLogin {
    @NotBlank(message = "Email é obrigatorio")
    private String email;
    @NotBlank(message = "Senha é obrigatorio")
    private String senha;
}
