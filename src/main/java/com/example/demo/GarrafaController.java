package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/garrafas")
public class GarrafaController {

    // Banco de dados em memória (os dados somem ao reiniciar a aplicação)
    private List<Garrafa> bancoDeGarrafas = new ArrayList<>();

    // Rota para Listar Todas as Garrafas
    // Método: GET | URL: http://localhost:8080/api/garrafas
    @GetMapping
    public ResponseEntity<List<Garrafa>> listarTodas() {
        return ResponseEntity.ok(bancoDeGarrafas);
    }

    // Rota para Buscar uma Garrafa por ID
    // Método: GET | URL: http://localhost:8080/api/garrafas/G001
    @GetMapping("/{id}")
    public ResponseEntity<Garrafa> buscarPorId(@PathVariable String id) {
        Optional<Garrafa> garrafaEncontrada = bancoDeGarrafas.stream()
                .filter(g -> g.getId().equals(id))
                .findFirst();

        if (garrafaEncontrada.isPresent()) {
            return ResponseEntity.ok(garrafaEncontrada.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Retorna erro 404
        }
    }

    // Rota para Cadastrar uma Nova Garrafa 
    // Método: POST | URL: http://localhost:8080/api/garrafas
    @PostMapping
    public ResponseEntity<Garrafa> cadastrar(@RequestBody Garrafa novaGarrafa) {
        bancoDeGarrafas.add(novaGarrafa);
        // Retorna status 201 (Created) e o objeto que acabou de ser salvo
        return ResponseEntity.status(HttpStatus.CREATED).body(novaGarrafa);
    }
}