package com.erpmarcenaria.SistemaGestao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardResponse {

    private int projetosNovos;
    private double receitaMes;
    private int estoquePercentual;
    private int itensEmEstoque;
    private int clientesPercentual;
    private int projetosEmAndamento;
    private int clientesAtivos;
    private int receitaPercentual;

    private List<ProjetoDTO> projetosRecentes;
}
