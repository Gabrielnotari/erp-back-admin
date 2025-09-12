package com.erpmarcenaria.SistemaGestao.service.impl;

import com.erpmarcenaria.SistemaGestao.dto.OrcamentoDTO;
import com.erpmarcenaria.SistemaGestao.dto.Response;
import com.erpmarcenaria.SistemaGestao.dto.SolicitacaoOrcamento;
import com.erpmarcenaria.SistemaGestao.entity.Fornecedor;
import com.erpmarcenaria.SistemaGestao.entity.Orcamento;
import com.erpmarcenaria.SistemaGestao.entity.Produto;
import com.erpmarcenaria.SistemaGestao.entity.User;
import com.erpmarcenaria.SistemaGestao.enums.StatusOrcamento;
import com.erpmarcenaria.SistemaGestao.enums.TipoOrcamento;
import com.erpmarcenaria.SistemaGestao.exceptions.NameValueRequiredException;
import com.erpmarcenaria.SistemaGestao.exceptions.NotFoundException;
import com.erpmarcenaria.SistemaGestao.repository.FornecedorRepository;
import com.erpmarcenaria.SistemaGestao.repository.OrcamentoRepository;
import com.erpmarcenaria.SistemaGestao.repository.ProdutoRepository;
import com.erpmarcenaria.SistemaGestao.service.OrcamentoService;
import com.erpmarcenaria.SistemaGestao.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrcamentoServiceImpl implements OrcamentoService {

    private final OrcamentoRepository orcamentoRepository;
    private final ModelMapper modelMapper;
    private final FornecedorRepository fornecedorRepository;
    private final UserService userService;
    private final ProdutoRepository produtoRepository;

    @Override
    public Response restockInventory(SolicitacaoOrcamento solicitacaoOrcamento) {

        Long produtotId = solicitacaoOrcamento.getProdutoId();
        Long fornecedorId = solicitacaoOrcamento.getFornecedorId();
        Integer quantidade = solicitacaoOrcamento.getQuantidade();

        if (fornecedorId == null) throw new NameValueRequiredException("Id do fornecedor é obrigatório");

        Produto produto = produtoRepository.findById(produtotId)
                .orElseThrow(()-> new NotFoundException("Produto não encontrado"));

        Fornecedor fornecedor = fornecedorRepository.findById(fornecedorId)
                .orElseThrow(()-> new NotFoundException("Fornecedor não encontrado"));

        User user = userService.getCurrentLoggedInUser();

        //atualizar qtdd estoque
        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + quantidade);
        produtoRepository.save(produto);

        //criar orcamento
        Orcamento orcamento = Orcamento.builder()
                .tipoOrcamento(TipoOrcamento.COMPRA)
                .status(StatusOrcamento.COMPLETO)
                .produto(produto)
                .user(user)
                .fornecedor(fornecedor)
                .totalProdutos(quantidade)
                .precoTotal(produto.getPreco().multiply(BigDecimal.valueOf(quantidade)))
                .descricao(solicitacaoOrcamento.getDescricao())
                .build();

        orcamentoRepository.save(orcamento);

        return Response.builder()
                .status(200)
                .message("Orçamento feito com sucesso")
                .build();
    }

    @Override
    public Response sell(SolicitacaoOrcamento solicitacaoOrcamento) {

        Long produtoId = solicitacaoOrcamento.getProdutoId();
        Integer quantidade = solicitacaoOrcamento.getQuantidade();


        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(()-> new NotFoundException("Produto não encontrado"));


        User user = userService.getCurrentLoggedInUser();

        //update the stock quantity and re-save
        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - quantidade);
        produtoRepository.save(produto);

        //create a transaction
        Orcamento orcamento = Orcamento.builder()
                .tipoOrcamento(TipoOrcamento.VENDA)
                .status(StatusOrcamento.COMPLETO)
                .produto(produto)
                .user(user)
                .totalProdutos(quantidade)
                .precoTotal(produto.getPreco().multiply(BigDecimal.valueOf(quantidade)))
                .descricao(solicitacaoOrcamento.getDescricao())
                .build();

        orcamentoRepository.save(orcamento);

        return Response.builder()
                .status(200)
                .message("Orçamento feito com sucesso")
                .build();
    }

    @Override
    public Response returnToFornecedor(SolicitacaoOrcamento solicitacaoOrcamento) {

        Long produtoId = solicitacaoOrcamento.getProdutoId();
        Long fornecedorId = solicitacaoOrcamento.getFornecedorId();
        Integer quantidade = solicitacaoOrcamento.getQuantidade();

        if (fornecedorId == null) throw new NameValueRequiredException("Id do fornecedor é obrigatório");

        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(()-> new NotFoundException("Produto não encontrado"));

        Fornecedor fornecedor = fornecedorRepository.findById(fornecedorId)
                .orElseThrow(()-> new NotFoundException("Fornecedor não encontrado"));

        User user = userService.getCurrentLoggedInUser();

        //update the stock quantity and re-save
        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - quantidade);
        produtoRepository.save(produto);

        //create a transaction
        Orcamento orcamento = Orcamento.builder()
                .tipoOrcamento(TipoOrcamento.RETORNAR_PARA_FORNECEDOR)
                .status(StatusOrcamento.PROCESSANDO)
                .produto(produto)
                .user(user)
                .fornecedor(fornecedor)
                .totalProdutos(quantidade)
                .precoTotal(BigDecimal.ZERO)
                .descricao(solicitacaoOrcamento.getDescricao())
                .build();

        orcamentoRepository.save(orcamento);

        return Response.builder()
                .status(200)
                .message("Orçamento retornado com sucesso")
                .build();
    }

    @Override
    public Response getAllOrcamentos(int page, int size, String searchText) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<Orcamento> orcamentoPage = orcamentoRepository.searchOrcamentos(searchText, pageable);

        List<OrcamentoDTO> orcamentoDTOS = modelMapper
                .map(orcamentoPage.getContent(), new TypeToken<List<OrcamentoDTO>>() {}.getType());

        orcamentoDTOS.forEach(orcamentoDTO -> {
            orcamentoDTO.setUser(null);
            orcamentoDTO.setProduto(null);
            orcamentoDTO.setFornecedor(null);
        });


        return Response.builder()
                .status(200)
                .message("sucesso")
                .orcamentos(orcamentoDTOS)
                .build();
    }

    @Override
    public Response getOrcamentoById(Long id) {
        Orcamento orcamento = orcamentoRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Orçamento não encontrado"));

        OrcamentoDTO orcamentoDTO = modelMapper.map(orcamento, OrcamentoDTO.class);

        orcamentoDTO.getUser().setOrcamentos(null); //removing the user orcamento list

        return Response.builder()
                .status(200)
                .message("sucesso")
                .orcamento(orcamentoDTO)
                .build();

    }

    @Override
    public Response getAllOrcamentoByMonthAndYear(int month, int year) {

        List<Orcamento> orcamentos = orcamentoRepository.findAllByMonthAndYear(month, year);

        List<OrcamentoDTO> orcamentoDTOS = modelMapper
                .map(orcamentos, new TypeToken<List<OrcamentoDTO>>() {}.getType());

        orcamentoDTOS.forEach(orcamentoDTOItem -> {
            orcamentoDTOItem.setUser(null);
            orcamentoDTOItem.setProduto(null);
            orcamentoDTOItem.setFornecedor(null);
        });


        return Response.builder()
                .status(200)
                .message("sucesso")
                .orcamentos(orcamentoDTOS)
                .build();
    }

    @Override
    public Response updateOrcamentoStatus(Long orcamentoId, StatusOrcamento statusOrcamento) {

        Orcamento existingOrcamento = orcamentoRepository.findById(orcamentoId)
                .orElseThrow(()-> new NotFoundException("Orçamento não encontrado"));

        existingOrcamento.setStatus(statusOrcamento);
        existingOrcamento.setDataAtualizacao(LocalDateTime.now());

        orcamentoRepository.save(existingOrcamento);

        return Response.builder()
                .status(200)
                .message("Status do orçamento atualizado com sucesso")
                .build();
    }

}
