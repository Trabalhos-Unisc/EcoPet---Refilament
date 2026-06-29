package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estoque")
public class EstoqueController {

    // Instanciado como um objeto ao em vez de uma lista
    private Estoque estoqueGeral = new Estoque();

    // Rota para Ver o Status do Estoque 
    // Método: GET | URL: http://localhost:8080/api/estoque
    @GetMapping
    public ResponseEntity<Estoque> verEstoque() {
        return ResponseEntity.ok(estoqueGeral);
    }

    // Rota para Dar Entrada em um Filamento 
    // Método: POST | URL: http://localhost:8080/api/estoque/entrada
    @PostMapping("/entrada")
    public ResponseEntity<Estoque> darEntrada(@RequestBody Filamento filamentoGerado) {
        estoqueGeral.entrada(filamentoGerado);
        return ResponseEntity.ok(estoqueGeral); // Retorna o estoque atualizado
    }

    // Rota para Solicitar Retirada (Consumo FIFO) 
    // Método: POST | URL: http://localhost:8080/api/estoque/saida?metros=5.0
    @PostMapping("/saida")
    public ResponseEntity<?> registrarSaida(@RequestParam double metros) {
        try {
            // Tenta retirar. Se não tiver saldo, a classe joga um IllegalArgumentException
            estoqueGeral.saida(metros);
            return ResponseEntity.ok(estoqueGeral);
            
        } catch (IllegalArgumentException erro) {
            // Se der erro (estoque insuficiente), devolvemos um status 400
            return ResponseEntity.badRequest().body(erro.getMessage());
        }
    }

    // Rota para Ver o Histórico de Rastreabilidade
    // Método: GET | URL: http://localhost:8080/api/estoque/historico
    @GetMapping("/historico")
    public ResponseEntity<List<String>> verHistorico() {
        return ResponseEntity.ok(estoqueGeral.getHistoricoSaidas());
    }
}