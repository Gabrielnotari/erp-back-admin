package com.erpmarcenaria.SistemaGestao.service.impl;

import com.erpmarcenaria.SistemaGestao.dto.ClienteDTO;
import com.erpmarcenaria.SistemaGestao.dto.ProjetoDTO;
import com.erpmarcenaria.SistemaGestao.dto.Response;
import com.erpmarcenaria.SistemaGestao.entity.Cliente;
import com.erpmarcenaria.SistemaGestao.entity.Projeto;
import com.erpmarcenaria.SistemaGestao.exceptions.NotFoundException;
import com.erpmarcenaria.SistemaGestao.repository.ClienteRepository;
import com.erpmarcenaria.SistemaGestao.service.ClienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final ModelMapper modelMapper;

    @Override
    public Response addCliente(ClienteDTO clienteDTO) {
        Cliente clienteToSave = modelMapper.map(clienteDTO, Cliente.class);
        clienteRepository.save(clienteToSave);

        return Response.builder()
                .status(200)
                .message("Cliente adicionado com sucesso")
                .build();
    }

    @Override
    public Response updateCliente(Long id, ClienteDTO clienteDTO) {
        Cliente existingCliente = clienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente não encontrado"));

        if (clienteDTO.getNome() != null) existingCliente.setNome(clienteDTO.getNome());
        if (clienteDTO.getEmail() != null) existingCliente.setEmail(clienteDTO.getEmail());
        if (clienteDTO.getTelefone() != null) existingCliente.setTelefone(clienteDTO.getTelefone());
        if (clienteDTO.getEndereco() != null) existingCliente.setEndereco(clienteDTO.getEndereco());

        clienteRepository.save(existingCliente);

        return Response.builder()
                .status(200)
                .message("Cliente atualizado com sucesso")
                .build();
    }

    @Override
    public Response getAllClientes() {
        List<Cliente> clientes = clienteRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        List<ClienteDTO> clienteDTOS = clientes.stream().map(cliente -> {
            ClienteDTO dto = modelMapper.map(cliente, ClienteDTO.class);
            dto.setQtdProjetos(
                    cliente.getProjetos() != null ? cliente.getProjetos().size() : 0
            );
            return dto;
        }).toList();

        return Response.builder()
                .status(200)
                .message("sucesso")
                .clientes(clienteDTOS)
                .build();
    }


    @Override
    public Response getClienteById(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente não encontrado"));

        ClienteDTO clienteDTO = modelMapper.map(cliente, ClienteDTO.class);

        return Response.builder()
                .status(200)
                .message("sucesso")
                .cliente(clienteDTO)
                .build();
    }

    @Override
    public Response getProjetosByCliente(Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new NotFoundException("Cliente não encontrado"));

        // pega a lista de projetos do cliente
        List<Projeto> projetos = cliente.getProjetos();

        // mapeia para DTO
        List<ProjetoDTO> projetoDTOS = modelMapper.map(projetos, new TypeToken<List<ProjetoDTO>>() {}.getType());

        return Response.builder()
                .status(200)
                .message("sucesso")
                .projetos(projetoDTOS)
                .build();
    }


    @Override
    public Response deleteCliente(Long id) {
        clienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente não encontrado"));

        clienteRepository.deleteById(id);

        return Response.builder()
                .status(200)
                .message("Cliente deletado com sucesso")
                .build();
    }
}
