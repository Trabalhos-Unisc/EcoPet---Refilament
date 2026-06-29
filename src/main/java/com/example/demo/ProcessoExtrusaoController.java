package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/extrusao")
public class ProcessoExtrusaoController {

    // Rota para Executar o Processo de Extrusão
    // Método: POST | URL: http://localhost:8080/api/extrusao/processar
    @PostMapping("/processar")
    public ResponseEntity<Filamento> processarLote(@RequestBody ProcessoExtrusao processo) {
        
        // O Spring Boot já montou o objeto "processo" com os dados que vieram no JSON.
        // Chamamos a sua lógica:
        Filamento filamentoGerado = processo.processar();
        
        // Retorna o filamento 
        return ResponseEntity.ok(filamentoGerado);
    }
}