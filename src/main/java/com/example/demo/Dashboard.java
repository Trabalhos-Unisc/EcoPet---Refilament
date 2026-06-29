package com.example.demo;

import java.util.List;

public class Dashboard {
    private Estoque estoque;
    private RelatorioSustentabilidade relatorio;
    private List<ProcessoExtrusao> processos;

    public Dashboard(Estoque estoque, RelatorioSustentabilidade relatorio, List<ProcessoExtrusao> processos) {
        this.estoque = estoque;
        this.relatorio = relatorio;
        this.processos = processos;
    }

    /**
     * Exibe o painel principal com o estado atual do sistema.
     */
    public void exibirPainel() {
        System.out.println("║         DASHBOARD ECOPET             ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║ ESTOQUE ATUAL                        ║");
        System.out.printf( "║   Filamentos cadastrados : %-9d      ║%n", estoque.getFilamentos().size());
        System.out.printf( "║   Total em metro         : %-9.2f    ║%n", estoque.getTotalMetros());
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║ PROCESSOS DE EXTRUSÃO                ║");
        System.out.printf( "║   Processos realizados   : %-9d║%n", processos.size());

        double totalFilamentoProduzido = processos.stream()
            .mapToDouble(p -> p.getLote().getFilamentoProd())
            .sum();
        System.out.printf( "║   Total filamento gerado : %-9.2f║%n", totalFilamentoProduzido);

        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║ LOTES NO RELATÓRIO                   ║");
        System.out.printf( "║   Total de lotes         : %-9d      ║%n", relatorio.getLotes().size());
        System.out.println("╚══════════════════════════════════════╝");
    }

    /**
     * Atualiza as métricas do dashboard gerando um novo relatório.
     */
    public void atualizarMetricas() {
        System.out.println("\n[Dashboard] Atualizando métricas...");
        relatorio.gerarRelatorio();
        System.out.println("[Dashboard] Métricas atualizadas com sucesso.");
    }

    public Estoque getEstoque() { return estoque; }
    public void setEstoque(Estoque estoque) { this.estoque = estoque; }

    public RelatorioSustentabilidade getRelatorio() { return relatorio; }
    public void setRelatorio(RelatorioSustentabilidade relatorio) { this.relatorio = relatorio; }

    public List<ProcessoExtrusao> getProcessos() { return processos; }
    public void setProcessos(List<ProcessoExtrusao> processos) { this.processos = processos; }
}
