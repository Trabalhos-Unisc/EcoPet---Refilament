package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final EcoPetService service;

    // Injeção do mesmo serviço central compartilhado
    public DashboardController(EcoPetService service) {
        this.service = service;
    }

    // Rota para Obter os Dados Consolidados do Dashboard
    // Método: GET | URL: http://localhost:8080/api/dashboard
    @GetMapping
    public ResponseEntity<DashboardDTO> obterPainel() {
        // Gera o relatório dinamicamente com os LOTES REAIS salvos no serviço
        RelatorioSustentabilidade relatorio = new RelatorioSustentabilidade(
                service.getCalculadora(), 
                service.getLotes()
        );
        
        // Executa a lógica de impressão no console
        relatorio.gerarRelatorio();

        // Calcula os totais com base nos dados reais do sistema
        double totalPlasticoSalvo = service.getLotes().stream().mapToDouble(Lote::getPesoTotal).sum();
        double totalFilamentoGerado = service.getProcessos().stream().mapToDouble(p -> p.getLote().getFilamentoProd()).sum();
        double co2Evitado = service.getCalculadora().calcCO2(totalPlasticoSalvo);
        int pecasEstimadas = service.getCalculadora().calcPecas(totalFilamentoGerado);

        DashboardDTO dto = new DashboardDTO(
            service.getEstoque().getFilamentos().size(),
            service.getEstoque().getTotalMetros(),
            service.getProcessos().size(),
            totalFilamentoGerado,
            service.getLotes().size(),
            totalPlasticoSalvo,
            co2Evitado,
            pecasEstimadas
        );

        return ResponseEntity.ok(dto);
    }
}