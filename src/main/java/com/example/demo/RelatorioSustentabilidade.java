package com.example.demo;

import java.util.List;

public class RelatorioSustentabilidade {
    private CalculadoraImpacto calculadora;
    private List<Lote> lotes;

    public RelatorioSustentabilidade(CalculadoraImpacto calculadora, List<Lote> lotes) {
        this.calculadora = calculadora;
        this.lotes = lotes;
    }

    /**
     * Gera e exibe o relatório de sustentabilidade consolidado de todos os lotes.
     */
    public void gerarRelatorio() {
        System.out.println("\n========================================");
        System.out.println("   RELATÓRIO DE SUSTENTABILIDADE ECOPET");
        System.out.println("========================================");

        double totalPlastico = 0;
        double totalFilamento = 0;
        int totalGarrafas = 0;

        for (Lote lote : lotes) {
            double plastico = calculadora.calcPlasticoSalvo(lote);
            double yield = calculadora.calcYield(lote);
            double rendMedio = calculadora.rendMedioPorGarrafa(lote);

            totalPlastico += plastico;
            totalFilamento += lote.getFilamentoProd();
            totalGarrafas += lote.getTotalGarrafas();

            System.out.println("\n-- Lote: " + lote.getId() + " (" + lote.getData() + ")");
            System.out.println("   Garrafas coletadas : " + lote.getTotalGarrafas());
            System.out.println("   Plástico salvo     : " + String.format("%.2f", plastico) + " kg");
            System.out.println("   Filamento produzido: " + String.format("%.2f", lote.getFilamentoProd()) + " m");
            System.out.println("   Yield              : " + String.format("%.2f", yield) + " m/kg");
            System.out.println("   Rend. por garrafa  : " + String.format("%.2f", rendMedio) + " m/garrafa");
        }

        double co2Total = calculadora.calcCO2(totalPlastico);
        int pecasTotal = calculadora.calcPecas(totalFilamento);

        System.out.println("\n========================================");
        System.out.println("   TOTAIS CONSOLIDADOS");
        System.out.println("========================================");
        System.out.println("Total de lotes       : " + lotes.size());
        System.out.println("Total de garrafas    : " + totalGarrafas);
        System.out.println("Total plástico salvo : " + String.format("%.2f", totalPlastico) + " kg");
        System.out.println("Total filamento prod.: " + String.format("%.2f", totalFilamento) + " m");
        System.out.println("CO2 evitado total    : " + String.format("%.2f", co2Total) + " kg");
        System.out.println("Peças estimadas      : " + pecasTotal);
        System.out.println("========================================\n");
    }

    /**
     * Exporta um resumo textual do relatório (simulação de exportação).
     */
    public String exportar() {
        StringBuilder sb = new StringBuilder();
        sb.append("RELATÓRIO ECOPET - EXPORTAÇÃO\n");
        sb.append("Total de lotes: ").append(lotes.size()).append("\n");

        for (Lote lote : lotes) {
            sb.append("Lote ").append(lote.getId())
              .append(" | Garrafas: ").append(lote.getTotalGarrafas())
              .append(" | Filamento: ").append(String.format("%.2f", lote.getFilamentoProd())).append(" m\n");
        }

        String resultado = sb.toString();
        System.out.println("[RelatorioSustentabilidade] Relatório exportado.");
        return resultado;
    }

    public CalculadoraImpacto getCalculadora() { return calculadora; }
    public void setCalculadora(CalculadoraImpacto calculadora) { this.calculadora = calculadora; }

    public List<Lote> getLotes() { return lotes; }
    public void setLotes(List<Lote> lotes) { this.lotes = lotes; }
}
