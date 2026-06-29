package com.example.demo;

public class Filamento {
    private String id;
    private double comprimento;
    private Lote loteOrigem;
    private double estoqueAtual;

    public Filamento() {
    }

    public Filamento(String id, double comprimento, Lote loteOrigem, double estoqueAtual) {
        this.id = id;
        this.comprimento = comprimento;
        this.loteOrigem = loteOrigem;
        this.estoqueAtual = estoqueAtual;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public double getComprimento() { return comprimento; }
    public void setComprimento(double comprimento) { this.comprimento = comprimento; }

    public Lote getLoteOrigem() { return loteOrigem; }
    public void setLoteOrigem(Lote loteOrigem) { this.loteOrigem = loteOrigem; }

    public double getEstoqueAtual() { return estoqueAtual; }
    public void setEstoqueAtual(double estoqueAtual) { this.estoqueAtual = estoqueAtual; }

    @Override
    public String toString() {
        return "Filamento{id='" + id + "', comprimento=" + comprimento + "m, estoqueAtual=" + estoqueAtual + "m}";
    }
}
