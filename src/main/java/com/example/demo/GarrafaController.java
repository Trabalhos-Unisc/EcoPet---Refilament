package com.example.demo;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/garrafas")
public class GarrafaController {

    private final EcoPetService service;

    public GarrafaController(EcoPetService service) {
        this.service = service;
    }

    // Rota para Listar Todas as Garrafas
    // Método: GET | URL: http://localhost:8080/api/garrafas
    @GetMapping
    public ResponseEntity<List<Garrafa>> listarTodas() {
        return ResponseEntity.ok(service.getGarrafas());
    }

    // Rota para Cadastrar uma Nova Garrafa Avulsa
    // Método: POST | URL: http://localhost:8080/api/garrafas
    @PostMapping
    public ResponseEntity<Garrafa> cadastrarGarrafa(@RequestBody Garrafa novaGarrafa) {
        service.adicionarGarrafa(novaGarrafa);
        return ResponseEntity.ok(novaGarrafa);
    }

    // Rota para Deletar uma Garrafa
    // Método: DELETE | URL: http://localhost:8080/api/garrafas/G001
    @org.springframework.web.bind.annotation.DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarGarrafa(@org.springframework.web.bind.annotation.PathVariable String id) {
        boolean removido = service.removerGarrafa(id);
        if (removido) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}