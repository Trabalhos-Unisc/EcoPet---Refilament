package com.example.demo;

public class DashboardDTO {
    private int filamentosEmEstoque;
    private double totalEstoqueMetros;
    private int processosRealizados;
    private double totalFilamentoGerado;
    private int totalDeLotes;
    private double totalPlasticoSalvoKg;
    private double co2EvitadoKg;
    private int pecasEstimadasTotal;

    public DashboardDTO(
        int filamentosEmEstoque, 
        double totalEstoqueMetros, 
        int processosRealizados,
        double totalFilamentoGerado, 
        int totalDeLotes, 
        double totalPlasticoSalvoKg,           
        double co2EvitadoKg, 
        int pecasEstimadasTotal) 
        {
        this.filamentosEmEstoque = filamentosEmEstoque;
        this.totalEstoqueMetros = totalEstoqueMetros;
        this.processosRealizados = processosRealizados;
        this.totalFilamentoGerado = totalFilamentoGerado;
        this.totalDeLotes = totalDeLotes;
        this.totalPlasticoSalvoKg = totalPlasticoSalvoKg;
        this.co2EvitadoKg = co2EvitadoKg;
        this.pecasEstimadasTotal = pecasEstimadasTotal;
    }

    // Getters para permitir a conversão em JSON pelo Spring Boot
    public int getFilamentosEmEstoque() { return filamentosEmEstoque; }
    public double getTotalEstoqueMetros() { return totalEstoqueMetros; }
    public int getProcessosRealizados() { return processosRealizados; }
    public double getTotalFilamentoGerado() { return totalFilamentoGerado; }
    public int getTotalDeLotes() { return totalDeLotes; }
    public double getTotalPlasticoSalvoKg() { return totalPlasticoSalvoKg; }
    public double getCo2EvitadoKg() { return co2EvitadoKg; }
    public int getPecasEstimadasTotal() { return pecasEstimadasTotal; }
}