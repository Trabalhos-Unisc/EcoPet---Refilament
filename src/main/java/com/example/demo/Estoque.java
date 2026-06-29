package com.example.demo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Estoque {
    /**
     * Fila FIFO: o filamento que entrou primeiro é o primeiro a ser consumido.
     * Garante rastreabilidade — sempre sabemos de qual lote saiu cada metro.
     */
    private Queue<Filamento> filamentos;
    private double totalMetros;

    /** Histórico de saídas para rastreabilidade completa. */
    private List<String> historicoSaidas;

    public Estoque() {
        this.filamentos = new LinkedList<>();
        this.totalMetros = 0.0;
        this.historicoSaidas = new ArrayList<>();
    }

    /**
     * Adiciona um filamento ao estoque (entra no fim da fila FIFO).
     *
     * @param f Filamento a ser adicionado
     */
    public void entrada(Filamento f) {
        filamentos.add(f);
        totalMetros += f.getComprimento();
        System.out.printf("[Estoque] Entrada: %s | %.2f m | Lote: %s%n",
            f.getId(), f.getComprimento(), f.getLoteOrigem().getId());
        System.out.printf("[Estoque] Total em estoque: %.2f m%n", totalMetros);
    }

    /**
     * Remove metros de filamento do estoque seguindo política FIFO.
     * Registra quais filamentos (e lotes de origem) foram consumidos.
     *
     * @param metros Quantidade de metros a ser retirada
     * @throws IllegalArgumentException se não houver filamento suficiente
     */
    public void saida(double metros) {
        if (metros > totalMetros) {
            throw new IllegalArgumentException(
                String.format("Estoque insuficiente! Solicitado: %.2f m | Disponível: %.2f m", metros, totalMetros)
            );
        }

        double restante = metros;
        StringBuilder rastreio = new StringBuilder();
        rastreio.append(String.format("Saída de %.2f m: ", metros));

        for (Filamento f : filamentos) {
            if (restante <= 0) break;
            double disponivel = f.getEstoqueAtual();
            if (disponivel <= 0) continue;

            if (disponivel >= restante) {
                f.setEstoqueAtual(disponivel - restante);
                rastreio.append(String.format("[%s %.2fm do lote %s] ",
                    f.getId(), restante, f.getLoteOrigem().getId()));
                restante = 0;
            } else {
                rastreio.append(String.format("[%s %.2fm do lote %s] ",
                    f.getId(), disponivel, f.getLoteOrigem().getId()));
                restante -= disponivel;
                f.setEstoqueAtual(0);
            }
        }

        totalMetros -= metros;
        String registro = rastreio.toString().trim();
        historicoSaidas.add(registro);
        System.out.println("[Estoque] " + registro);
        System.out.printf("[Estoque] Total restante: %.2f m%n", totalMetros);
    }

    /** Exibe o histórico completo de saídas com rastreabilidade por lote. */
    public void exibirHistoricoSaidas() {
        System.out.println("\n[Estoque] Histórico de saídas:");
        if (historicoSaidas.isEmpty()) {
            System.out.println("  Nenhuma saída registrada.");
        } else {
            for (int i = 0; i < historicoSaidas.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + historicoSaidas.get(i));
            }
        }
    }

    public List<Filamento> getFilamentos() { return new ArrayList<>(filamentos); }

    public double getTotalMetros() { return totalMetros; }

    public List<String> getHistoricoSaidas() { return historicoSaidas; }

    @Override
    public String toString() {
        return String.format("Estoque{filamentos=%d, totalMetros=%.2f m}", filamentos.size(), totalMetros);
    }
}
