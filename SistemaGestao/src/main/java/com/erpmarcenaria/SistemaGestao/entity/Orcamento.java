package com.erpmarcenaria.SistemaGestao.entity;

import com.erpmarcenaria.SistemaGestao.auditoria.Auditable;
import com.erpmarcenaria.SistemaGestao.enums.StatusOrcamento;
import com.erpmarcenaria.SistemaGestao.enums.TipoOrcamento;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "orcamentos")
public class Orcamento extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer totalProdutos;

    private BigDecimal precoTotal;

    @Enumerated(EnumType.STRING)
    private TipoOrcamento tipoOrcamento;

    @Enumerated(EnumType.STRING)
    private StatusOrcamento status;

    private String descricao;

    private LocalDateTime dataAtualizacao;

    private final LocalDateTime dataCriacao = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fornecedor_id")
    private Fornecedor fornecedor;

    @Override
    public String toString() {
        return "Orcamento{" +
                "id=" + id +
                ", totalProdutos=" + totalProdutos +
                ", precoTotal=" + precoTotal +
                ", tipoOrcamento=" + tipoOrcamento +
                ", statusOrcamento=" + status +
                ", descricao='" + descricao + '\'' +
                ", dataAtualizacao=" + dataAtualizacao +
                ", dataCriacao=" + dataCriacao +
                '}';
    }
}
