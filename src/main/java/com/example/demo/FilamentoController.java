package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/filamentos")
public class FilamentoController {

    private final EcoPetService service;

    // Injeção do mesmo serviço centralizado
    public FilamentoController(EcoPetService service) {
        this.service = service;
    }

    // Listar Filamentos do Estoque Central
    // Método: GET | URL: http://localhost:8080/api/filamentos
    @GetMapping
    public ResponseEntity<List<Filamento>> listarTodos() {
        // Pega a lista estruturada de dentro do estoque geral do serviço
        return ResponseEntity.ok(service.getEstoque().getFilamentos());
    }

    // Rota para Buscar um Filamento por ID
    // Método: GET | URL: http://localhost:8080/api/filamentos/F001
    @GetMapping("/{id}")
    public ResponseEntity<Filamento> buscarPorId(@PathVariable String id) {
        java.util.Optional<Filamento> filamentoEncontrado = service.getEstoque().getFilamentos().stream()
                .filter(f -> f.getId().equalsIgnoreCase(id)) // equalsIgnoreCase evita problemas com maiúsculas/minúsculas
                .findFirst();

        if (filamentoEncontrado.isPresent()) {
            return ResponseEntity.ok(filamentoEncontrado.get());
        }
        
        // Se o ID não for encontrado no estoque, devolve status 404
        return ResponseEntity.notFound().build();
    }

    // Rota para Cadastrar um Novo Filamento
    // Método: POST | URL: http://localhost:8080/api/filamentos
    @PostMapping
    public ResponseEntity<Filamento> cadastrar(@RequestBody Filamento novoFilamento) {
        service.getEstoque().entrada(novoFilamento);
        return ResponseEntity.ok(novoFilamento);
    }
}