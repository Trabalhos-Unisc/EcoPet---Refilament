package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lotes")
public class LoteController {

    private final EcoPetService service;

    // Construtor: O Spring Boot injeta automaticamente o mesmo Singleton que o Dashboard e o Estoque usam
    public LoteController(EcoPetService service) {
        this.service = service;
    }

    // Rota para Listar Todos os Lotes 
    // Método: GET | URL: http://localhost:8080/api/lotes
    @GetMapping
    public ResponseEntity<List<Lote>> listarTodos() {
        return ResponseEntity.ok(service.getLotes());
    }

    // Rota para Buscar um Lote Específico por ID 
    // Método: GET | URL: http://localhost:8080/api/lotes/L001
    @GetMapping("/{id}")
    public ResponseEntity<Lote> buscarPorId(@PathVariable String id) {
        Optional<Lote> loteEncontrado = service.getLotes().stream()
                .filter(l -> l.getId().equals(id))
                .findFirst();

        if (loteEncontrado.isPresent()) {
            return ResponseEntity.ok(loteEncontrado.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Rota para Cadastrar um Novo Lote
    // Método: POST | URL: http://localhost:8080/api/lotes
    @PostMapping
    public ResponseEntity<Lote> cadastrar(@RequestBody Lote novoLote) {
        service.getLotes().add(novoLote);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoLote);
    }

    // Rota para Colocar uma Garrafa dentro de um Lote Existente
    // Método: POST | URL: http://localhost:8080/api/lotes/L001/garrafas
    @PostMapping("/{id}/garrafas")
    public ResponseEntity<?> adicionarGarrafaAoLote(@PathVariable String id, @RequestBody Garrafa novaGarrafa) {
        // Buscamos o lote real dentro do serviço
        Lote lote = service.buscarLotePorId(id);
        
        if (lote != null) {
            lote.adicionarGarrafa(novaGarrafa);
            return ResponseEntity.ok(lote); // Devolve o lote atualizado para conferência
        }
        
        // Se o usuário tentar botar garrafa em um lote que não criou antes:
        return ResponseEntity.badRequest().body("Erro: O lote com ID " + id + " não existe.");
    }
}