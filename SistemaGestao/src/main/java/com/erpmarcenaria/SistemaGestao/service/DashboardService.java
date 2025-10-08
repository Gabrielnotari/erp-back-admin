package com.erpmarcenaria.SistemaGestao.service;

import com.erpmarcenaria.SistemaGestao.dto.DashboardResponse;
import com.erpmarcenaria.SistemaGestao.dto.ProjetoDTO;
import com.erpmarcenaria.SistemaGestao.entity.Projeto;
import com.erpmarcenaria.SistemaGestao.enums.StatusProjeto;
import com.erpmarcenaria.SistemaGestao.repository.ClienteRepository;
import com.erpmarcenaria.SistemaGestao.repository.OrcamentoRepository;
import com.erpmarcenaria.SistemaGestao.repository.ProdutoRepository;
import com.erpmarcenaria.SistemaGestao.repository.ProjetoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ProjetoRepository projetoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final OrcamentoRepository orcamentoRepository;

    public DashboardResponse getDashboard() {

        DashboardResponse response = new DashboardResponse();

        // --- DADOS GERAIS ---
        response.setClientesAtivos((int) clienteRepository.count());
        response.setProjetosEmAndamento((int) projetoRepository.countByStatus(StatusProjeto.EM_PRODUCAO));
        response.setItensEmEstoque(produtoRepository.countAllBy());
        response.setReceitaMes(calcularReceitaMesAtual());
        response.setClientesPercentual(12);
        response.setProjetosNovos(3);
        response.setEstoquePercentual(-8);
        response.setReceitaPercentual(23);

        // --- PROJETOS RECENTES ---
        List<Projeto> recentes = projetoRepository.findTop5ByOrderByDataInicioDesc();
        List<ProjetoDTO> projetosRecentes = recentes.stream().map(this::mapToProjetoDTO).toList();

        response.setProjetosRecentes(projetosRecentes);

        return response;
    }

    private ProjetoDTO mapToProjetoDTO(Projeto projeto) {
        ProjetoDTO dto = new ProjetoDTO();
        dto.setId(projeto.getId());
        dto.setNomeProjeto(projeto.getNomeProjeto());
        dto.setDescricao(projeto.getDescricao());
        dto.setStatus(projeto.getStatus());
        dto.setPrevisaoEntrega(projeto.getPrevisaoEntrega());
        dto.setDataInicio(projeto.getDataInicio());
        dto.setValor(projeto.getValor());
        dto.setClienteId(projeto.getCliente().getId());
        dto.setClienteNome(projeto.getCliente().getNome());
        return dto;
    }

    private double calcularReceitaMesAtual() {
        // Exemplo fictício — substitua pela soma real do mês atual
        return 45231.0;
    }
}
