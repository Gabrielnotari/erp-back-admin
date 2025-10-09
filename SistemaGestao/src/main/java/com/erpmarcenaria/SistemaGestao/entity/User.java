package com.erpmarcenaria.SistemaGestao.entity;

import com.erpmarcenaria.SistemaGestao.auditoria.Auditable;
import com.erpmarcenaria.SistemaGestao.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "Email é obrigatório")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Senha é obrigatório")
    private String senha;

    @NotBlank(message = "Numero Telefone é obrigatório")
    @Column(name = "numero_telefone")
    private String numeroTelefone;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany(mappedBy = "user")
    private List<Orcamento> orcamentos;

    @Column(name = "created_at")
    private final LocalDateTime createdAt = LocalDateTime.now();

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", password='" + senha + '\'' +
                ", phoneNumber='" + numeroTelefone + '\'' +
                ", role=" + role +
                ", createdAt=" + createdAt +
                '}';
    }
}
