package com.erpmarcenaria.SistemaGestao.repository;

import com.erpmarcenaria.SistemaGestao.entity.Projeto;
import com.erpmarcenaria.SistemaGestao.enums.StatusProjeto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
    List<Projeto> findTop5ByOrderByDataInicioDesc();


    long countByStatus(StatusProjeto status);
}
