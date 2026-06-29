package com.example.demo;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service // anotação para o spring boot criar uma instância desta classe para o sistema todo usar.
public class EcoPetService {

    // Centralização de todos os dados do sistema
    private final List<Garrafa> garrafas = new ArrayList<>();
    private final List<Lote> lotes = new ArrayList<>();
    private final List<ProcessoExtrusao> processos = new ArrayList<>();
    private final Estoque estoque = new Estoque();
    private final CalculadoraImpacto calculadora = new CalculadoraImpacto();

    // Métodos para o Controller de Garrafas
    public List<Garrafa> getGarrafas() { return garrafas; }
    public void adicionarGarrafa(Garrafa g) { garrafas.add(g); }

    // Métodos para o Controller de Lotes
    public List<Lote> getLotes() { return lotes; }
    public void adicionarLote(Lote l) { lotes.add(l); }
    public Lote buscarLotePorId(String id) {
        return lotes.stream()
                .filter(l -> l.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    // Métodos para o Controller de Extrusão e Estoque
    public List<ProcessoExtrusao> getProcessos() { return processos; }
    public void adicionarProcesso(ProcessoExtrusao p) { processos.add(p); }
    
    public Estoque getEstoque() { return estoque; }
    public CalculadoraImpacto getCalculadora() { return calculadora; }
}