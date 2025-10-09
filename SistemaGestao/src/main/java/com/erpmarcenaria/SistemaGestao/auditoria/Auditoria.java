package com.erpmarcenaria.SistemaGestao.auditoria;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "auditoria")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String entidade;
    private Long idRegistro;
    private String acao;
    private String usuario;

    @Column(name = "role_usuario")
    private String roleUsuario;


    @Column(length = 1000)
    private String detalhes;

    private LocalDateTime dataHora;
}

