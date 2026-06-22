import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


public class Main {
    public static void main(String[] args) {

        // ── 1. Criar garrafas coletadas ──────────────────────────────
        // Peso médio de garrafa PET 500ml: 28g; 2L: ~42g
        Garrafa g1 = new Garrafa("G001", 0.028, LocalDate.of(2024, 6, 1), "L001");
        Garrafa g2 = new Garrafa("G002", 0.028, LocalDate.of(2024, 6, 1), "L001");
        Garrafa g3 = new Garrafa("G003", 0.042, LocalDate.of(2024, 6, 2), "L001");
        Garrafa g4 = new Garrafa("G004", 0.028, LocalDate.of(2024, 6, 3), "L002");
        Garrafa g5 = new Garrafa("G005", 0.042, LocalDate.of(2024, 6, 3), "L002");

        // ── 2. Criar e montar lotes ───────────────────────────────────
        Lote lote1 = new Lote("L001", LocalDate.of(2024, 6, 1));
        lote1.adicionarGarrafa(g1);
        lote1.adicionarGarrafa(g2);
        lote1.adicionarGarrafa(g3);

        Lote lote2 = new Lote("L002", LocalDate.of(2024, 6, 3));
        lote2.adicionarGarrafa(g4);
        lote2.adicionarGarrafa(g5);

        System.out.println("Lotes criados:");
        System.out.println("  " + lote1);
        System.out.println("  " + lote2);

        // ── 3. Processar extrusão (com percentual de perda real) ──────
        System.out.println("\n── PROCESSAMENTO DE EXTRUSÃO ──");
        // 8% de perda = aparas e falhas típicas de extrusão de PET reciclado
        // rendimento base de PET: ~100 m/kg para filamento 1.75mm
        ProcessoExtrusao proc1 = new ProcessoExtrusao(lote1, 100.0, LocalDate.of(2024, 6, 5), 0.08);
        ProcessoExtrusao proc2 = new ProcessoExtrusao(lote2, 100.0, LocalDate.of(2024, 6, 6), 0.08);

        Filamento fil1 = proc1.processar();
        Filamento fil2 = proc2.processar();

        // ── 4. Registrar filamentos no estoque (FIFO) ─────────────────
        System.out.println("\n── MOVIMENTAÇÃO DE ESTOQUE (FIFO) ──");
        Estoque estoque = new Estoque();
        estoque.entrada(fil1);
        estoque.entrada(fil2);

        // Saída rastreável: desconta do filamento mais antigo primeiro
        estoque.saida(3.0);
        estoque.exibirHistoricoSaidas();

        // ── 5. Calcular impacto (parâmetros configuráveis) ────────────
        System.out.println("\n── CÁLCULO DE IMPACTO ──");
        // CO2: 2.9 kg evitado por kg reciclado (referência bibliográfica conservadora)
        CalculadoraImpacto calc = new CalculadoraImpacto(2.9, 5.0);
        double pesoTotalGeral = lote1.getPesoTotal() + lote2.getPesoTotal();
        calc.calcCO2(pesoTotalGeral);
        calc.calcPecas(estoque.getTotalMetros());
        calc.calcYield(lote1);
        calc.rendMedioPorGarrafa(lote2);

        // ── 6. Relatório e Dashboard ──────────────────────────────────
        List<Lote> todosOsLotes = Arrays.asList(lote1, lote2);
        RelatorioSustentabilidade relatorio = new RelatorioSustentabilidade(calc, todosOsLotes);

        List<ProcessoExtrusao> processos = Arrays.asList(proc1, proc2);
        Dashboard dashboard = new Dashboard(estoque, relatorio, processos);

        dashboard.exibirPainel();
        dashboard.atualizarMetricas();

        // ── 7. Exportar ───────────────────────────────────────────────
        System.out.println("\n── EXPORTAÇÃO ──");
        System.out.println(relatorio.exportar());
    }
}
