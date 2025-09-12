package com.erpmarcenaria.SistemaGestao.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "produtos")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome e obrigatório")
    private String nome;

    @NotBlank(message = "Sku é obrigatório")
    @Column(unique = true)
    private String sku;

    @Positive
    private BigDecimal preco;

    @Min(value = 0, message = "Não pode ser menor que zero")
    private Integer quantidadeEstoque;

    private String descricao;

    private String imageUrl;

    private LocalDateTime dataExpiracao;

    private  LocalDateTime dataAtualizacao;

    private final LocalDateTime dataCriacao = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", sku='" + sku + '\'' +
                ", preco=" + preco +
                ", quantidadeEstoque=" + quantidadeEstoque +
                ", description='" + descricao + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", dataExpiracao=" + dataExpiracao +
                ", dataAtualizacao=" + dataAtualizacao +
                ", dataCriacao=" + dataCriacao +
                '}';
    }
}
