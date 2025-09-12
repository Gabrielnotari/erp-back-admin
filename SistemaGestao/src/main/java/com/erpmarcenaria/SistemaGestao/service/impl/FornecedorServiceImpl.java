package com.erpmarcenaria.SistemaGestao.service.impl;

import com.erpmarcenaria.SistemaGestao.dto.FornecedorDTO;
import com.erpmarcenaria.SistemaGestao.dto.Response;
import com.erpmarcenaria.SistemaGestao.entity.Fornecedor;
import com.erpmarcenaria.SistemaGestao.exceptions.NotFoundException;
import com.erpmarcenaria.SistemaGestao.repository.FornecedorRepository;
import com.erpmarcenaria.SistemaGestao.service.FornecedorService;
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
public class FornecedorServiceImpl implements FornecedorService {

    private final FornecedorRepository fornecedorRepository;
    private final ModelMapper modelMapper;


    @Override
    public Response addFornecedor(FornecedorDTO fornecedorDTO) {
        Fornecedor fornecedorToSave = modelMapper.map(fornecedorDTO, Fornecedor.class);
        fornecedorRepository.save(fornecedorToSave);

        return Response.builder()
                .status(200)
                .message("Fornecedor adicionado com sucesso")
                .build();
    }

    @Override
    public Response updateFornecedor(Long id, FornecedorDTO fornecedorDTO) {

        Fornecedor existingFornecedor = fornecedorRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Fornecedor não encontrado"));

        if (fornecedorDTO.getNome() != null) existingFornecedor.setNome(fornecedorDTO.getNome());
        if (fornecedorDTO.getEndereco() != null) existingFornecedor.setEndereco(fornecedorDTO.getEndereco());

        fornecedorRepository.save(existingFornecedor);

        return Response.builder()
                .status(200)
                .message("Fornecedor atualizado com sucesso")
                .build();
    }

    @Override
    public Response getAllFornecedores() {

        List<Fornecedor> categories = fornecedorRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        List<FornecedorDTO> fornecedorDTOS = modelMapper.map(categories, new TypeToken<List<FornecedorDTO>>() {}.getType());

        return Response.builder()
                .status(200)
                .message("sucesso")
                .fornecedores(fornecedorDTOS)
                .build();
    }

    @Override
    public Response getFornecedorById(Long id) {

        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Fornecedor não encontrado"));

        FornecedorDTO fornecedorDTO = modelMapper.map(fornecedor, FornecedorDTO.class);

        return Response.builder()
                .status(200)
                .message("sucesso")
                .fornecedor(fornecedorDTO)
                .build();
    }

    @Override
    public Response deleteFornecedor(Long id) {

        fornecedorRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Fornecedor não encontrado"));

        fornecedorRepository.deleteById(id);

        return Response.builder()
                .status(200)
                .message("Fornecedor deletado com sucesso")
                .build();
    }

}
