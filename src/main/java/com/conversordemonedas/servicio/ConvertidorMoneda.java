package com.conversordemonedas.servicio;

import com.conversordemonedas.modelo.RespuestaCambio;

import java.io.IOException;

public class ConvertidorMoneda {
    private final ExchangeRateApiCliente apiCliente;

    public ConvertidorMoneda(ExchangeRateApiCliente apiCliente) {
        this.apiCliente = apiCliente;
    }

    public RespuestaCambio convertir(double monto, String monedaOrigen, String monedaDestino, String apiKey)
            throws IOException, InterruptedException {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero.");
        }
        double tasaCambio = apiCliente.obtenerTasa(monedaOrigen, monedaDestino, apiKey);
        double convertido = calcularMontoConvertido(monto, tasaCambio);
        return new RespuestaCambio(monto, monedaOrigen, monedaDestino, tasaCambio, convertido);
    }

    public double calcularMontoConvertido(double monto, double tasaCambio) {
        if (tasaCambio <= 0) {
            throw new IllegalArgumentException("La tasa de cambio debe ser mayor que cero.");
        }
        return monto * tasaCambio;
    }
}
