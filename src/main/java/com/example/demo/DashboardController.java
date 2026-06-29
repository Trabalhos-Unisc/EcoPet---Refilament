package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    // Instâncias "Singletons" para simular o estado consolidado da aplicação
    // Evita duplicidade
    private CalculadoraImpacto calculadora = new CalculadoraImpacto();
    private List<Lote> bancoDeLotesCompartilhado = new ArrayList<>();
    private List<ProcessoExtrusao> bancoDeProcessos = new ArrayList<>();
    private Estoque estoqueGeral = new Estoque();

    // Rota para Obter os Dados Consolidados do Dashboard
    // Método: GET | URL: http://localhost:8080/api/dashboard
    @GetMapping
    public ResponseEntity<DashboardDTO> obterPainel() {
        // Cria o relatório de sustentabilidade com base nos lotes atuais
        RelatorioSustentabilidade relatorio = new RelatorioSustentabilidade(calculadora, bancoDeLotesCompartilhado);
        
        // Executa a lógica de impressão no console
        relatorio.gerarRelatorio();

        // Calcula os totais agregados para o Front-End
        double totalPlasticoSalvo = bancoDeLotesCompartilhado.stream().mapToDouble(Lote::getPesoTotal).sum();
        double totalFilamentoGerado = bancoDeProcessos.stream().mapToDouble(p -> p.getLote().getFilamentoProd()).sum();
        double co2Evitado = calculadora.calcCO2(totalPlasticoSalvo);
        int pecasEstimadas = calculadora.calcPecas(totalFilamentoGerado);

        // Monta um objeto de transferência de dados (DTO) amigável para o JSON da Web
        DashboardDTO dto = new DashboardDTO(
            estoqueGeral.getFilamentos().size(),
            estoqueGeral.getTotalMetros(),
            bancoDeProcessos.size(),
            totalFilamentoGerado,
            bancoDeLotesCompartilhado.size(),
            totalPlasticoSalvo,
            co2Evitado,
            pecasEstimadas
        );

        return ResponseEntity.ok(dto);
    }

    // Rota Utilitária para Alimentar Dados de Teste Rapido 
    // Método: POST | URL: http://localhost:8080/api/dashboard/mock-dados
    @PostMapping("/mock-dados")
    public ResponseEntity<String> popularDadosDeTeste() {
        // Criando lote de simulação
        Lote lote1 = new Lote("LOTE-MOCK-01", java.time.LocalDate.now());
        lote1.adicionarGarrafa(new Garrafa("G-MOCK-1", 0.030, java.time.LocalDate.now(), "LOTE-MOCK-01"));
        lote1.adicionarGarrafa(new Garrafa("G-MOCK-2", 0.035, java.time.LocalDate.now(), "LOTE-MOCK-01"));
        
        // Processando a extrusão simulada
        ProcessoExtrusao proc = new ProcessoExtrusao(lote1, 333.3, java.time.LocalDate.now(), 0.08);
        Filamento fil = proc.processar();
        
        // Alimentando o estado do dashboard
        bancoDeLotesCompartilhado.add(lote1);
        bancoDeProcessos.add(proc);
        estoqueGeral.entrada(fil);

        return ResponseEntity.ok("Dados de teste gerados com sucesso para validar o Dashboard!");
    }
}