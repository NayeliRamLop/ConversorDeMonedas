package com.conversordemonedas.servicio;

import com.conversordemonedas.modelo.ExchangeRatePairResponse;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class ExchangeRateApiCliente {
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6";

    private final Gson gson;
    private final HttpClient httpClient;

    public ExchangeRateApiCliente(Gson gson) {
        this.gson = gson;
        this.httpClient = HttpClient.newHttpClient();
    }

    public double obtenerTasa(String monedaOrigen, String monedaDestino, String apiKey)
            throws IOException, InterruptedException {
        String origen = encode(monedaOrigen.toUpperCase());
        String destino = encode(monedaDestino.toUpperCase());
        String key = encode(apiKey);
        String url = String.format("%s/%s/pair/%s/%s", BASE_URL, key, origen, destino);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("HTTP " + response.statusCode() + " al consultar ExchangeRate-API");
        }

        ExchangeRatePairResponse data = gson.fromJson(response.body(), ExchangeRatePairResponse.class);
        if (data == null) {
            throw new RuntimeException("Respuesta vacia de ExchangeRate-API");
        }
        if (!"success".equalsIgnoreCase(data.result())) {
            String error = data.errorType() == null ? "desconocido" : data.errorType();
            throw new RuntimeException("ExchangeRate-API devolvio error: " + error);
        }
        if (data.conversionRate() <= 0) {
            throw new RuntimeException("Tasa de conversion invalida recibida desde la API");
        }
        return data.conversionRate();
    }

    private String encode(String valor) {
        return URLEncoder.encode(valor, StandardCharsets.UTF_8);
    }
}
