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

    // Banco de dados em memória para os Lotes
    private List<Lote> bancoDeLotes = new ArrayList<>();

    // Rota para Listar Todos os Lotes 
    // Método: GET | URL: http://localhost:8080/api/lotes
    @GetMapping
    public ResponseEntity<List<Lote>> listarTodos() {
        return ResponseEntity.ok(bancoDeLotes);
    }

    // Rota para Buscar um Lote Específico por ID 
    // Método: GET | URL: http://localhost:8080/api/lotes/L001
    @GetMapping("/{id}")
    public ResponseEntity<Lote> buscarPorId(@PathVariable String id) {
        Optional<Lote> loteEncontrado = bancoDeLotes.stream()
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
        bancoDeLotes.add(novoLote);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoLote);
    }
}