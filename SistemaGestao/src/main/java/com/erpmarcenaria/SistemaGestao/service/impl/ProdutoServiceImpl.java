package com.erpmarcenaria.SistemaGestao.service.impl;

import com.erpmarcenaria.SistemaGestao.dto.ProdutoDTO;
import com.erpmarcenaria.SistemaGestao.dto.Response;
import com.erpmarcenaria.SistemaGestao.entity.Categoria;
import com.erpmarcenaria.SistemaGestao.entity.Produto;
import com.erpmarcenaria.SistemaGestao.exceptions.NotFoundException;
import com.erpmarcenaria.SistemaGestao.repository.CategoriaRepository;
import com.erpmarcenaria.SistemaGestao.repository.ProdutoRepository;
import com.erpmarcenaria.SistemaGestao.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ModelMapper modelMapper;
    private final CategoriaRepository categoriaRepository;

    private static final String IMAGE_DIRECTORY = System.getProperty("user.dir") + "/produto-image/";

    private static final String IMAGE_DIRECTOR_FRONTEND = "C:\\Users\\gabri\\Downloads";

    @Override
    public Response saveProduto(ProdutoDTO produtoDTO, MultipartFile imageFile) {

        Categoria categoria = categoriaRepository.findById(produtoDTO.getCategoriaId())
                .orElseThrow(()-> new NotFoundException("Categoria não encontrada"));


        Produto produtoToSave = Produto.builder()
                .nome(produtoDTO.getNome())
                .sku(produtoDTO.getSku())
                .preco(produtoDTO.getPreco())
                .quantidadeEstoque(produtoDTO.getQuantidadeEstoque())
                .descricao(produtoDTO.getDescricao())
                .categoria(categoria)
                .build();

        if (imageFile != null){
            String imagePath = saveImageToFrontendPublicFolder(imageFile);
            produtoToSave.setImageUrl(imagePath);
        }

        //salvar produto no banco
        produtoRepository.save(produtoToSave);
        return Response.builder()
                .status(200)
                .message("Produto salvo com sucesso")
                .build();
    }

    @Override
    public Response updateProduto(ProdutoDTO produtoDTO, MultipartFile imageFile) {

        Produto existingProduto = produtoRepository.findById(produtoDTO.getProdutoId())
                .orElseThrow(()-> new NotFoundException("Produto não encontrado"));


        if (imageFile != null && !imageFile.isEmpty()){
            String imagePath = saveImageToFrontendPublicFolder(imageFile);
            existingProduto.setImageUrl(imagePath);
        }

        if (produtoDTO.getCategoriaId() != null && produtoDTO.getCategoriaId() > 0){

            Categoria categoria = categoriaRepository.findById(produtoDTO.getCategoriaId())
                    .orElseThrow(()-> new NotFoundException("Categoria não encontrada"));
            existingProduto.setCategoria(categoria);
        }

        //check and update fiedls

        if (produtoDTO.getNome() !=null && !produtoDTO.getNome().isBlank()){
            existingProduto.setNome(produtoDTO.getNome());
        }

        if (produtoDTO.getSku() !=null && !produtoDTO.getSku().isBlank()){
            existingProduto.setSku(produtoDTO.getSku());
        }

        if (produtoDTO.getDescricao() !=null && !produtoDTO.getDescricao().isBlank()){
            existingProduto.setDescricao(produtoDTO.getDescricao());
        }

        if (produtoDTO.getPreco() !=null && produtoDTO.getPreco().compareTo(BigDecimal.ZERO) >=0){
            existingProduto.setPreco(produtoDTO.getPreco());
        }

        if (produtoDTO.getQuantidadeEstoque() !=null && produtoDTO.getQuantidadeEstoque() >=0){
            existingProduto.setQuantidadeEstoque(produtoDTO.getQuantidadeEstoque());
        }

        //atualizar o produto
        produtoRepository.save(existingProduto);
        return Response.builder()
                .status(200)
                .message("Produto atualizado com sucesso")
                .build();

    }

    @Override
    public Response getAllProduto() {

        List<Produto> produtos = produtoRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        List<ProdutoDTO> produtoDTOS = modelMapper.map(produtos, new TypeToken<List<ProdutoDTO>>() {}.getType());

        return Response.builder()
                .status(200)
                .message("sucesso")
                .produtos(produtoDTOS)
                .build();
    }

    @Override
    public Response getProdutoById(Long id) {

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Produto não encontrado"));


        return Response.builder()
                .status(200)
                .message("sucesso")
                .produto(modelMapper.map(produto, ProdutoDTO.class))
                .build();
    }

    @Override
    public Response deleteProduto(Long id) {

        produtoRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Produto não encontrado"));

        produtoRepository.deleteById(id);

        return Response.builder()
                .status(200)
                .message("Produto deletado com sucesso")
                .build();
    }

    private String saveImageToFrontendPublicFolder(MultipartFile imageFile){

        if (!imageFile.getContentType().startsWith("image/")){
            throw new IllegalArgumentException("Only image files are allowed");
        }

        File directory = new File(IMAGE_DIRECTOR_FRONTEND);

        if (!directory.exists()){
            directory.mkdir();
            log.info("Directory was created");
        }

        String uniqueFileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();

        String imagePath = IMAGE_DIRECTOR_FRONTEND + uniqueFileName;

        try {
            File desctinationFile = new File(imagePath);
            imageFile.transferTo(desctinationFile);

        }catch (Exception e){
            throw new IllegalArgumentException("Erro ocorrido enquanto salva imagem" + e.getMessage());
        }

        return "produtos/"+uniqueFileName;
    }

    private String saveImage(MultipartFile imageFile){
        //validate image check
        if (!imageFile.getContentType().startsWith("image/")){
            throw new IllegalArgumentException("Only image files are allowed");
        }
        //create the directory to store images if it doesn't exist
        File directory = new File(IMAGE_DIRECTORY);

        if (!directory.exists()){
            directory.mkdir();
            log.info("Directory was created");
        }
        //generate unique file name for the image
        String uniqueFileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
        //get the absolute path of the image
        String imagePath = IMAGE_DIRECTORY + uniqueFileName;

        try {
            File desctinationFile = new File(imagePath);
            imageFile.transferTo(desctinationFile); //we are transfering(writing to this folder)

        }catch (Exception e){
            throw new IllegalArgumentException("Erro ocorrido enquanto salva imagem" + e.getMessage());
        }

        return imagePath;

    }
}
