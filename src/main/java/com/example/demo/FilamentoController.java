package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/filamentos")
public class FilamentoController {

    // Banco de dados em memória para os Filamentos
    private List<Filamento> bancoDeFilamentos = new ArrayList<>();

    // Rota para Listar Todos os Filamentos
    // Método: GET | URL: http://localhost:8080/api/filamentos
    @GetMapping
    public ResponseEntity<List<Filamento>> listarTodos() {
        return ResponseEntity.ok(bancoDeFilamentos);
    }

    // Rota para Buscar um Filamento por ID
    // Método: GET | URL: http://localhost:8080/api/filamentos/F001
    @GetMapping("/{id}")
    public ResponseEntity<Filamento> buscarPorId(@PathVariable String id) {
        Optional<Filamento> filamentoEncontrado = bancoDeFilamentos.stream()
                .filter(f -> f.getId().equals(id))
                .findFirst();

        if (filamentoEncontrado.isPresent()) {
            return ResponseEntity.ok(filamentoEncontrado.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Rota para Cadastrar um Novo Filamento
    // Método: POST | URL: http://localhost:8080/api/filamentos
    @PostMapping
    public ResponseEntity<Filamento> cadastrar(@RequestBody Filamento novoFilamento) {
        bancoDeFilamentos.add(novoFilamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoFilamento);
    }
}