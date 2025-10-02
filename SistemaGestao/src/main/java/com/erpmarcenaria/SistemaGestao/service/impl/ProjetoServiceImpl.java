package com.erpmarcenaria.SistemaGestao.service.impl;

import com.erpmarcenaria.SistemaGestao.dto.ProjetoDTO;
import com.erpmarcenaria.SistemaGestao.dto.Response;
import com.erpmarcenaria.SistemaGestao.entity.Cliente;
import com.erpmarcenaria.SistemaGestao.entity.Projeto;
import com.erpmarcenaria.SistemaGestao.exceptions.NotFoundException;
import com.erpmarcenaria.SistemaGestao.repository.ClienteRepository;
import com.erpmarcenaria.SistemaGestao.repository.ProjetoRepository;
import com.erpmarcenaria.SistemaGestao.service.ProjetoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjetoServiceImpl implements ProjetoService {

    private final ProjetoRepository projetoRepository;
    private final ModelMapper modelMapper;
    private final ClienteRepository clienteRepository;

    // 🔹 Converte Projeto → ProjetoDTO incluindo nome do cliente
    private ProjetoDTO convertToDTO(Projeto projeto) {
        ProjetoDTO dto = modelMapper.map(projeto, ProjetoDTO.class);

        if (projeto.getCliente() != null) {
            dto.setClienteId(projeto.getCliente().getId());
            dto.setClienteNome(projeto.getCliente().getNome());
        }

        return dto;
    }

    @Override
    public Response addProjeto(ProjetoDTO projetoDTO) {
        Cliente cliente = clienteRepository.findById(projetoDTO.getClienteId())
                .orElseThrow(() -> new NotFoundException("Cliente não encontrado"));

        Projeto projetoToSave = modelMapper.map(projetoDTO, Projeto.class);
        projetoToSave.setCliente(cliente);

        projetoRepository.save(projetoToSave);

        return Response.builder()
                .status(200)
                .message("Projeto adicionado com sucesso")
                .build();
    }

    @Override
    public Response updateProjeto(Long id, ProjetoDTO projetoDTO) {
        Projeto existingProjeto = projetoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Projeto não encontrado"));

        if (projetoDTO.getNomeProjeto() != null) existingProjeto.setNomeProjeto(projetoDTO.getNomeProjeto());
        if (projetoDTO.getDescricao() != null) existingProjeto.setDescricao(projetoDTO.getDescricao());
        if (projetoDTO.getStatus() != null) existingProjeto.setStatus(projetoDTO.getStatus());
        if (projetoDTO.getPrevisaoEntrega() != null) existingProjeto.setPrevisaoEntrega(projetoDTO.getPrevisaoEntrega());
        if (projetoDTO.getDataInicio() != null) existingProjeto.setDataInicio(projetoDTO.getDataInicio());
        if (projetoDTO.getValor() != null) existingProjeto.setValor(projetoDTO.getValor());

        projetoRepository.save(existingProjeto);

        return Response.builder()
                .status(200)
                .message("Projeto atualizado com sucesso")
                .build();
    }

    @Override
    public Response getAllProjetos() {
        List<Projeto> projetos = projetoRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        List<ProjetoDTO> projetoDTOS = projetos.stream()
                .map(this::convertToDTO)
                .toList();

        return Response.builder()
                .status(200)
                .message("sucesso")
                .projetos(projetoDTOS)
                .build();
    }

    @Override
    public Response getProjetoById(Long id) {
        Projeto projeto = projetoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Projeto não encontrado"));

        ProjetoDTO projetoDTO = convertToDTO(projeto);

        return Response.builder()
                .status(200)
                .message("sucesso")
                .projeto(projetoDTO)
                .build();
    }

    @Override
    public Response deleteProjeto(Long id) {
        projetoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Projeto não encontrado"));

        projetoRepository.deleteById(id);

        return Response.builder()
                .status(200)
                .message("Projeto deletado com sucesso")
                .build();
    }
}
