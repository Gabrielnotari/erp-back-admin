package com.erpmarcenaria.SistemaGestao.repository;

import com.erpmarcenaria.SistemaGestao.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
