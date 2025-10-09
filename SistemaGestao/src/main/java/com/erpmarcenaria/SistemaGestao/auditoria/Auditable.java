package com.erpmarcenaria.SistemaGestao.auditoria;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;

//Essa classe é herdada por todas as  entidades (Produto, Cliente, Projeto...).
//Toda vez que o Hibernate fizer um INSERT, UPDATE ou DELETE
//os métodos @PrePersist, @PreUpdate, @PreRemove serão chamados automaticamente.
//Dentro deles, chamamos uma classe auxiliar chamada AuditoriaListener.

@MappedSuperclass
@Getter
@Setter
public abstract class Auditable {

    @Column(name = "criado_por")
    private String criadoPor;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_por")
    private String atualizadoPor;

    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

    @PrePersist
    public void onCreate() {
        this.criadoEm = LocalDateTime.now();
        this.criadoPor = getUsuarioLogado();
        AuditoriaListener.registrarAuditoria(this, "CREATE");
    }

    @PreUpdate
    public void onUpdate() {
        this.atualizadoEm = LocalDateTime.now();
        this.atualizadoPor = getUsuarioLogado();
        AuditoriaListener.registrarAuditoria(this, "UPDATE");
    }

    @PreRemove
    public void onDelete() {
        AuditoriaListener.registrarAuditoria(this, "DELETE");
    }

    private String getUsuarioLogado() {
        try {
            return SecurityContextHolder.getContext().getAuthentication().getName();
        } catch (Exception e) {
            return "sistema";
        }
    }
}
