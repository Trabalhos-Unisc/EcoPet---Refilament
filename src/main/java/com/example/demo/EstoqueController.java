package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estoque")
public class EstoqueController {

    // Em vez de "new", nós pedimos o service para o Spring Boot
    private final EcoPetService service;

    // Construtor: O Spring Boot injeta automaticamente o Singleton aqui
    public EstoqueController(EcoPetService service) {
        this.service = service;
    }

    // Rota para Ver o Status do Estoque 
    // Método: GET | URL: http://localhost:8080/api/estoque
    @GetMapping
    public ResponseEntity<Estoque> verEstoque() {
        return ResponseEntity.ok(service.getEstoque());
    }

    // Rota para Dar Entrada em um Filamento 
    // Método: POST | URL: http://localhost:8080/api/estoque/entrada
    @PostMapping("/entrada")
    public ResponseEntity<Estoque> darEntrada(@RequestBody Filamento filamentoGerado) {
        service.getEstoque().entrada(filamentoGerado);
        return ResponseEntity.ok(service.getEstoque()); // Retorna o estoque atualizado
    }

    // Rota para Solicitar Retirada (Consumo FIFO) 
    // Método: POST | URL: http://localhost:8080/api/estoque/saida?metros=5.0
    @PostMapping("/saida")
    public ResponseEntity<?> registrarSaida(@RequestParam double metros) {
        try {
            // Tenta retirar. Se não tiver saldo, a classe joga um IllegalArgumentException
            service.getEstoque().saida(metros);
            return ResponseEntity.ok(service.getEstoque());
            
        } catch (IllegalArgumentException erro) {
            // Se der erro (estoque insuficiente), devolvemos um status 400
            return ResponseEntity.badRequest().body(erro.getMessage());
        }
    }

    // Rota para Ver o Histórico de Rastreabilidade
    // Método: GET | URL: http://localhost:8080/api/estoque/historico
    @GetMapping("/historico")
    public ResponseEntity<List<String>> verHistorico() {
        return ResponseEntity.ok(service.getEstoque().getHistoricoSaidas());
    }
}