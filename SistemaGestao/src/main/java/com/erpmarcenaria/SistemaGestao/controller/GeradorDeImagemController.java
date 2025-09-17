package com.erpmarcenaria.SistemaGestao.controller;

import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImageOptions;
import org.springframework.ai.image.ImageOptionsBuilder;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/imagem")
public class GeradorDeImagemController {

    private final ImageModel imageModel;

    public GeradorDeImagemController(ImageModel imageModel) {
        this.imageModel = imageModel;
    }

    @GetMapping
    public String gerarImagem(@RequestParam String prompt) {

        // Configurações da imagem
        ImageOptions options = ImageOptionsBuilder.builder()
                .height(1024)
                .width(1024)
                .responseFormat("url") // CORRETO: 'url' ou 'b64_json'
                .build();

        // Chamada da API
        var response = imageModel.call(new ImagePrompt(prompt, options));

        // Retorna a URL da imagem gerada
        return response.getResult().getOutput().getUrl();
    }
}
