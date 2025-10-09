package com.erpmarcenaria.SistemaGestao.auditoria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import java.lang.reflect.Field;
import java.time.LocalDateTime;

@Component
public class AuditoriaListener {

    private static AuditoriaRepository auditoriaRepository;

    @Autowired
    public void init(AuditoriaRepository repository) {
        auditoriaRepository = repository;
    }

    public static void registrarAuditoria(Object entidade, String acao) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            Auditoria auditoria = new Auditoria();
            auditoria.setEntidade(entidade.getClass().getSimpleName());
            auditoria.setAcao(acao);
            auditoria.setDataHora(LocalDateTime.now());

            if (auth != null) {
                auditoria.setUsuario(auth.getName());
                auditoria.setRoleUsuario(
                        auth.getAuthorities().stream()
                                .map(Object::toString)
                                .findFirst()
                                .orElse("SEM_ROLE")
                );
            } else {
                auditoria.setUsuario("sistema");
                auditoria.setRoleUsuario("N/A");
            }

            // Tenta pegar o ID da entidade, se existir
            try {
                Field idField = entidade.getClass().getDeclaredField("id");
                idField.setAccessible(true);
                Object idValue = idField.get(entidade);
                auditoria.setIdRegistro(idValue != null ? Long.valueOf(idValue.toString()) : null);
            } catch (Exception ignored) {}

            // Registra o estado atual como detalhes (pode serializar em JSON)
            auditoria.setDetalhes(entidade.toString());

            auditoriaRepository.save(auditoria);
        } catch (Exception e) {
            System.err.println("Erro ao registrar auditoria: " + e.getMessage());
        }
    }
}
