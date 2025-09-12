package com.erpmarcenaria.SistemaGestao.service.impl;

import com.erpmarcenaria.SistemaGestao.dto.CategoriaDTO;
import com.erpmarcenaria.SistemaGestao.dto.Response;
import com.erpmarcenaria.SistemaGestao.entity.Categoria;
import com.erpmarcenaria.SistemaGestao.exceptions.NotFoundException;
import com.erpmarcenaria.SistemaGestao.repository.CategoriaRepository;
import com.erpmarcenaria.SistemaGestao.service.CategoriaService;
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
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final ModelMapper modelMapper;

    @Override
    public Response createCategory(CategoriaDTO categoriaDTO) {
        Categoria categoryToSave = modelMapper.map(categoriaDTO, Categoria.class);
        categoriaRepository.save(categoryToSave);

        return Response.builder()
                .status(200)
                .message("Categoria criada com sucesso")
                .build();
    }

    @Override
    public Response getAllCategories() {

        List<Categoria> categorias = categoriaRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        List<CategoriaDTO> categoryDTOS = modelMapper.map(categorias, new TypeToken<List<CategoriaDTO>>() {}.getType());

        return Response.builder()
                .status(200)
                .message("sucesso")
                .categorias(categoryDTOS)
                .build();
    }

    @Override
    public Response getCategoryById(Long id) {

        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Categoria não encontrada"));
        CategoriaDTO categoriaDTO = modelMapper.map(categoria, CategoriaDTO.class);

        return Response.builder()
                .status(200)
                .message("sucesso")
                .categoria(categoriaDTO)
                .build();
    }

    @Override
    public Response updateCategory(Long id, CategoriaDTO categoriaDTO) {

        Categoria existingCategory = categoriaRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Categoria não encontrada"));

        existingCategory.setNome(categoriaDTO.getNome());
        categoriaRepository.save(existingCategory);

        return Response.builder()
                .status(200)
                .message("Categoria atualizada com sucesso")
                .build();

    }

    @Override
    public Response deleteCategory(Long id) {

        categoriaRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Categoria não encontrada"));

        categoriaRepository.deleteById(id);

        return Response.builder()
                .status(200)
                .message("Categoria deletada com sucesso")
                .build();
    }
}
