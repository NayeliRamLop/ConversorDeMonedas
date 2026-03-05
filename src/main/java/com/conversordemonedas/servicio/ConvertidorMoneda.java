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
        double tasaCambio = apiCliente.obtenerTasa(monedaOrigen, monedaDestino, apiKey);
        double convertido = monto * tasaCambio;
        return new RespuestaCambio(monto, monedaOrigen, monedaDestino, tasaCambio, convertido);
    }
}
