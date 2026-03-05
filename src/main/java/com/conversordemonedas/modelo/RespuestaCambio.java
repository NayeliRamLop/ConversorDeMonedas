package com.conversordemonedas.modelo;

public record RespuestaCambio(
        double montoOriginal,
        String monedaOrigen,
        String monedaDestino,
        double tasaCambio,
        double montoConvertido
) {
}
