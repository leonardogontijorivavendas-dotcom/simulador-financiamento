package com.exemplo.financiamento;

import org.springframework.web.bind.annotation.*;
import java.util.Locale;

@RestController
@RequestMapping("/api/financiamento")
@CrossOrigin(origins = "*")
public class FinanciamentoController {

    @GetMapping("/calcular")
    public ResultadoFinanciamento calcular(
            @RequestParam("valorImovel") double valorImovel,
            @RequestParam("entrada") double entrada,
            @RequestParam("taxaJuros") double taxaJuros,
            @RequestParam("prazo") int prazo,
            @RequestParam("tipo") String tipo) {

        double valorFinanciado = valorImovel - entrada;
        if (valorFinanciado < 0) valorFinanciado = 0;
        double jurosMensal = taxaJuros / 100.0;
        double[] parcelas;

        if (tipo == null) tipo = "PRICE";
        tipo = tipo.toUpperCase(Locale.ROOT);

        if (tipo.equals("PRICE")) {
            parcelas = calcularPrice(valorFinanciado, jurosMensal, prazo);
        } else {
            parcelas = calcularSAC(valorFinanciado, jurosMensal, prazo);
        }

        return new ResultadoFinanciamento(valorFinanciado, parcelas);
    }

    private double[] calcularPrice(double valor, double juros, int meses) {
        if (meses <= 0) return new double[0];
        if (juros == 0) {
            double[] parcelasZero = new double[meses];
            double parcela = valor / meses;
            for (int i = 0; i < meses; i++) parcelasZero[i] = parcela;
            return parcelasZero;
        }
        double fator = Math.pow(1 + juros, meses);
        double parcelaFixa = valor * (juros * fator) / (fator - 1);
        double[] parcelas = new double[meses];
        for (int i = 0; i < meses; i++) parcelas[i] = parcelaFixa;
        return parcelas;
    }

    private double[] calcularSAC(double valor, double juros, int meses) {
        if (meses <= 0) return new double[0];
        double amortizacao = valor / meses;
        double[] parcelas = new double[meses];
        for (int i = 0; i < meses; i++) {
            double saldoDevedor = valor - (amortizacao * i);
            parcelas[i] = amortizacao + (saldoDevedor * juros);
        }
        return parcelas;
    }
}
