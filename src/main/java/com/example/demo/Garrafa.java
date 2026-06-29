package com.example.demo;

import java.time.LocalDate;

public class Garrafa {
    private String id;
    private double peso;
    private LocalDate dataColeta;
    private String lote;

    public Garrafa() {
    }

    public Garrafa(String id, double peso, LocalDate dataColeta, String lote) {
        this.id = id;
        this.peso = peso;
        this.dataColeta = dataColeta;
        this.lote = lote;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }

    public LocalDate getDataColeta() { return dataColeta; }
    public void setDataColeta(LocalDate dataColeta) { this.dataColeta = dataColeta; }

    public String getLote() { return lote; }
    public void setLote(String lote) { this.lote = lote; }

    @Override
    public String toString() {
        return "Garrafa{id='" + id + "', peso=" + peso + "kg, dataColeta=" + dataColeta + "}";
    }
}
