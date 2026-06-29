package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/extrusao")
public class ProcessoExtrusaoController {

    private final EcoPetService service;

    public ProcessoExtrusaoController(EcoPetService service) {
        this.service = service;
    }

    // Rota para Processar a Extrusão
    // Método: POST | URL: http://localhost:8080/api/extrusao/processar
    @PostMapping("/processar")
    public ResponseEntity<?> processarExtrusao(@RequestBody ProcessoExtrusao processoRequisicao) {
        
        // Busca o lote real que está guardado no sistema usando o ID que veio no JSON
        Lote loteReal = service.buscarLotePorId(processoRequisicao.getLote().getId());
        
        if (loteReal == null) {
            return ResponseEntity.badRequest().body("Lote não encontrado no sistema!");
        }

        // Vincula o lote real ao processo
        processoRequisicao.setLote(loteReal);

        // Calcula perdas e gera o filamento
        Filamento filamentoGerado = processoRequisicao.processar();

        // Salva o processo no Dashboard
        service.adicionarProcesso(processoRequisicao);

        // Joga o filamento gerado direto no estoque automaticamente
        service.getEstoque().entrada(filamentoGerado);

        return ResponseEntity.ok(filamentoGerado);
    }
}