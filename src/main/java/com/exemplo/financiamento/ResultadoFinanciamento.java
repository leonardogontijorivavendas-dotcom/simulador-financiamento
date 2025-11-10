package com.exemplo.financiamento;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ResultadoFinanciamento {

    private double valorFinanciado;
    private double[] parcelas;
    private double valorTotal;
    private double jurosTotais;

    public ResultadoFinanciamento(double valorFinanciado, double[] parcelas) {
        this.valorFinanciado = arredondar(valorFinanciado);
        this.parcelas = parcelas;

        double total = 0;
        for (double p : parcelas) total += p;
        this.valorTotal = arredondar(total);
        this.jurosTotais = arredondar(this.valorTotal - this.valorFinanciado);
    }

    private double arredondar(double valor) {
        return BigDecimal.valueOf(valor)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    // Getters e Setters para o Spring gerar o JSON corretamente
    public double getValorFinanciado() {
        return valorFinanciado;
    }

    public void setValorFinanciado(double valorFinanciado) {
        this.valorFinanciado = valorFinanciado;
    }

    public double[] getParcelas() {
        return parcelas;
    }

    public void setParcelas(double[] parcelas) {
        this.parcelas = parcelas;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public double getJurosTotais() {
        return jurosTotais;
    }

    public void setJurosTotais(double jurosTotais) {
        this.jurosTotais = jurosTotais;
    }
}
