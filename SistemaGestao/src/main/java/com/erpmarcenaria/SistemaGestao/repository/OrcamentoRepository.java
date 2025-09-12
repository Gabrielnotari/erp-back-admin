package com.erpmarcenaria.SistemaGestao.repository;

import com.erpmarcenaria.SistemaGestao.entity.Orcamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrcamentoRepository extends JpaRepository<Orcamento, Long> {
    @Query("SELECT o FROM Orcamento o " +
            "WHERE YEAR(o.dataCriacao) = :year AND MONTH(o.dataCriacao) = :month")
    List<Orcamento> findAllByMonthAndYear(@Param("month") int month, @Param("year") int year);


    @Query("SELECT o FROM Orcamento o " +
            "LEFT JOIN o.produto p " +
            "WHERE (:searchText IS NULL OR " +
            "LOWER(o.descricao) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(o.status) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(p.nome) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(p.sku) LIKE LOWER(CONCAT('%', :searchText, '%')))")
    Page<Orcamento> searchOrcamentos(@Param("searchText") String searchText, Pageable pageable);
}
