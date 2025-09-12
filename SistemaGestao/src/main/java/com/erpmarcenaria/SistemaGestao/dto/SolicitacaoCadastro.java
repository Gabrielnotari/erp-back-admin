package com.erpmarcenaria.SistemaGestao.dto;

import com.erpmarcenaria.SistemaGestao.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SolicitacaoCadastro {
    @NotBlank(message = "Nome é obrigatorio")
    private String nome;
    @NotBlank(message = "Email é obrigatorio")
    private String email;
    @NotBlank(message = "Senha é obrigatorio")
    private String senha;
    @NotBlank(message = "numeroTelefone é obrigatorio")
    private String numeroTelefone;
    private UserRole role;
}
