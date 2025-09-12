package com.erpmarcenaria.SistemaGestao.repository;

import com.erpmarcenaria.SistemaGestao.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
