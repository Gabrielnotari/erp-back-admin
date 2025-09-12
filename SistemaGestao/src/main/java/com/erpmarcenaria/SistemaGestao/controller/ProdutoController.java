package com.erpmarcenaria.SistemaGestao.controller;

import com.erpmarcenaria.SistemaGestao.dto.ProdutoDTO;
import com.erpmarcenaria.SistemaGestao.dto.Response;
import com.erpmarcenaria.SistemaGestao.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
@Slf4j
public class ProdutoController {

    private final ProdutoService produtoService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> saveProduto(
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            @RequestParam("nome") String  nome,
            @RequestParam("sku") String  sku,
            @RequestParam("preco") BigDecimal preco,
            @RequestParam("quantidadeEstoque") Integer  quantidadeEstoque,
            @RequestParam("categoriaId") Long  categoriaId,
            @RequestParam(value = "descricao", required = false) String  descricao
    ) {
        ProdutoDTO produtoDTO = new ProdutoDTO();

        produtoDTO.setNome(nome);
        produtoDTO.setSku(sku);
        produtoDTO.setPreco(preco);
        produtoDTO.setQuantidadeEstoque(quantidadeEstoque);
        produtoDTO.setCategoriaId(categoriaId);
        produtoDTO.setDescricao(descricao);

        System.out.println(produtoDTO);


        return ResponseEntity.ok(produtoService.saveProduto(produtoDTO, imageFile));
    }

    @PutMapping("/atualizar")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateProduto(
            @RequestParam(value = "imageFile", required=false) MultipartFile imageFile,
            @RequestParam(value = "nome",required = false) String  nome,
            @RequestParam(value = "sku",required = false) String  sku,
            @RequestParam(value = "preco",required = false) BigDecimal preco,
            @RequestParam(value = "quantidadeEstoque",required = false) Integer  quantidadeEstoque,
            @RequestParam(value = "produtoId",required = true) Long  produtoId,
            @RequestParam(value = "categoriaId",required = false) Long  categoriaId,
            @RequestParam(value = "descricao", required = false) String  descricao
    ) {
        ProdutoDTO produtoDTO = new ProdutoDTO();

        produtoDTO.setNome(nome);
        produtoDTO.setSku(sku);
        produtoDTO.setPreco(preco);
        produtoDTO.setQuantidadeEstoque(quantidadeEstoque);
        produtoDTO.setCategoriaId(categoriaId);
        produtoDTO.setProdutoId(produtoId);
        produtoDTO.setDescricao(descricao);

        return ResponseEntity.ok(produtoService.updateProduto(produtoDTO, imageFile));
    }

    @GetMapping("/todos")
    public ResponseEntity<Response> getAllProduto() {
        return ResponseEntity.ok(produtoService.getAllProduto());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Response> getProdutoById(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.getProdutoById(id));
    }


    @DeleteMapping("/deletar/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteProduto(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.deleteProduto(id));
    }

}
