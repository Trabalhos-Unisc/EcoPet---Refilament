package com.example.demo;

import java.time.LocalDate;

public class ProcessoExtrusao {
    private Lote lote;
    private double rendimento;
    private LocalDate dataProc;

    /** Percentual de perda do processo (aparas, falhas de extrusão). Ex: 0.08 = 8% */
    private double percentualPerda;

    /** Peso em kg efetivamente perdido no processo (calculado em processar()). */
    private double pesoPerda;

    public ProcessoExtrusao(Lote lote, double rendimento, LocalDate dataProc) {
        this.lote = lote;
        this.rendimento = rendimento;
        this.dataProc = dataProc;
        this.percentualPerda = 0.0;
        this.pesoPerda = 0.0;
    }

    public ProcessoExtrusao(Lote lote, double rendimento, LocalDate dataProc, double percentualPerda) {
        this.lote = lote;
        this.rendimento = rendimento;
        this.dataProc = dataProc;
        this.percentualPerda = percentualPerda;
        this.pesoPerda = 0.0;
    }

    /**
     * Processa o lote e gera um Filamento descontando as perdas do processo.
     * O peso aproveitado = pesoTotal * (1 - percentualPerda).
     * A perda é registrada para uso no índice de circularidade.
     *
     * @return Filamento gerado a partir do lote
     */
    public Filamento processar() {
        double pesoTotal = lote.getPesoTotal();
        this.pesoPerda = pesoTotal * percentualPerda;
        double pesoAproveitado = pesoTotal - pesoPerda;
        double comprimentoGerado = pesoAproveitado * rendimento;

        String filamentoId = "FIL-" + lote.getId() + "-" + dataProc;
        Filamento filamento = new Filamento(filamentoId, comprimentoGerado, lote, comprimentoGerado);

        lote.setFilamentoProd(comprimentoGerado);

        System.out.println("[ProcessoExtrusao] Lote processado: " + lote.getId());
        System.out.printf("  Peso total das garrafas : %.3f kg%n", pesoTotal);
        System.out.printf("  Perda no processo (%.0f%%): %.3f kg%n", percentualPerda * 100, pesoPerda);
        System.out.printf("  Peso aproveitado        : %.3f kg%n", pesoAproveitado);
        System.out.printf("  Rendimento              : %.1f m/kg%n", rendimento);
        System.out.printf("  Filamento gerado        : %.2f m%n", comprimentoGerado);

        return filamento;
    }

    public Lote getLote() { return lote; }
    public void setLote(Lote lote) { this.lote = lote; }

    public double getRendimento() { return rendimento; }
    public void setRendimento(double rendimento) { this.rendimento = rendimento; }

    public LocalDate getDataProc() { return dataProc; }
    public void setDataProc(LocalDate dataProc) { this.dataProc = dataProc; }

    public double getPercentualPerda() { return percentualPerda; }
    public void setPercentualPerda(double percentualPerda) { this.percentualPerda = percentualPerda; }

    /** Retorna o peso perdido no processo. Disponível apenas após chamar processar(). */
    public double getPesoPerda() { return pesoPerda; }

    @Override
    public String toString() {
        return "ProcessoExtrusao{lote=" + lote.getId() + ", rendimento=" + rendimento + " m/kg, dataProc=" + dataProc + "}";
    }
}
