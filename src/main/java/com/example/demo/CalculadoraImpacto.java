package com.example.demo;

import org.springframework.stereotype.Component;

@Component
public class CalculadoraImpacto {

    /**
     * Emissão de CO2 evitada por kg de PET reciclado em vez de produzido virgem.
     * Fonte: Ecoinvent / literatura de ACV para PET (média ~3,4 kg CO2e/kg PET virgem,
     * menos ~0,5 kg CO2e/kg PET reciclado = ~2,9 kg evitados).
     * Valor conservador adotado: 2.9 kg CO2e/kg
     */
    private double co2PorKgPet;

    /**
     * Metros de filamento consumidos por peça impressa em média.
     */
    private double metrosPorPeca;

    /** Construtor com valores padrão baseados em referências bibliográficas. */
    public CalculadoraImpacto() {
        this.co2PorKgPet = 2.9;
        this.metrosPorPeca = 5.0;
    }

    /** Construtor que permite configurar os parâmetros com dados reais do projeto. */
    public CalculadoraImpacto(double co2PorKgPet, double metrosPorPeca) {
        this.co2PorKgPet = co2PorKgPet;
        this.metrosPorPeca = metrosPorPeca;
    }

    /**
     * Calcula o plástico salvo (em kg) com base no peso total das garrafas do lote.
     *
     * @param l Lote a ser analisado
     * @return Plástico salvo em kg
     */
    public double calcPlasticoSalvo(Lote l) {
        double pesoTotal = l.getPesoTotal();
        System.out.println("[CalculadoraImpacto] Plástico salvo do lote " + l.getId() + ": " + pesoTotal + " kg");
        return pesoTotal;
    }

    /**
     * Calcula a quantidade de CO2 equivalente evitada pela reciclagem do PET.
     * Representa a diferença entre produzir PET virgem vs. reciclar.
     *
     * @param kg Quilogramas de PET reciclado
     * @return CO2 evitado em kg
     */
    public double calcCO2(double kg) {
        if (kg <= 0) return 0;
        double co2Evitado = kg * co2PorKgPet;
        System.out.println("[CalculadoraImpacto] CO2 evitado: " + String.format("%.2f", co2Evitado) + " kg para " + kg + " kg de PET");
        return co2Evitado;
    }

    /**
     * Calcula o número estimado de peças que podem ser impressas
     * com uma dada quantidade de filamento.
     *
     * @param metros Metros de filamento disponíveis
     * @return Número estimado de peças
     */
    public int calcPecas(double metros) {
        if (metrosPorPeca <= 0 || metros <= 0) {
            return 0;
        }
        int pecas = (int) (metros / metrosPorPeca);
        System.out.println("[CalculadoraImpacto] Peças estimadas com " + metros + " m: " + pecas);
        return pecas;
    }

    /**
     * Calcula o rendimento total do lote (metros de filamento produzido por kg de PET).
     *
     * @param l Lote a ser analisado
     * @return Rendimento em metros por kg
     */
    public double calcYield(Lote l) {
        double pesoTotal = l.getPesoTotal();
        if (pesoTotal <= 0) {
            System.out.println("[CalculadoraImpacto] Lote sem peso registrado.");
            return 0;
        }
        double yield = l.getFilamentoProd() / pesoTotal;
        System.out.println("[CalculadoraImpacto] Yield do lote " + l.getId() + ": " + yield + " m/kg");
        return yield;
    }

    /**
     * Calcula o rendimento médio por garrafa do lote (metros de filamento por garrafa).
     *
     * @param l Lote a ser analisado
     * @return Metros de filamento por garrafa
     */
    public double rendMedioPorGarrafa(Lote l) {
        int totalGarrafas = l.getTotalGarrafas();
        if (totalGarrafas <= 0) {
            System.out.println("[CalculadoraImpacto] Lote sem garrafas registradas.");
            return 0;
        }
        double rendMedio = l.getFilamentoProd() / totalGarrafas;
        System.out.println("[CalculadoraImpacto] Rendimento médio por garrafa no lote " + l.getId() + ": " + rendMedio + " m/garrafa");
        return rendMedio;
    }

    public double getCo2PorKgPet() { return co2PorKgPet; }
    public void setCo2PorKgPet(double co2PorKgPet) { this.co2PorKgPet = co2PorKgPet; }

    public double getMetrosPorPeca() { return metrosPorPeca; }
    public void setMetrosPorPeca(double metrosPorPeca) { this.metrosPorPeca = metrosPorPeca; }
}