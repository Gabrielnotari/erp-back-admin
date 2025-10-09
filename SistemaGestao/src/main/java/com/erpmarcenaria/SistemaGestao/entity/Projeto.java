package com.erpmarcenaria.SistemaGestao.entity;

import com.erpmarcenaria.SistemaGestao.auditoria.Auditable;
import com.erpmarcenaria.SistemaGestao.enums.StatusProjeto;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="projetos")
public class Projeto extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeProjeto;
    private String descricao;

    @Enumerated(EnumType.STRING)
    private StatusProjeto status;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate previsaoEntrega;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataInicio;


    private BigDecimal valor;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
}
