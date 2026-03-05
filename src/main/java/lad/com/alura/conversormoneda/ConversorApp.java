package lad.com.alura.conversormoneda;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConversorApp {
    public static void main(String[] args) throws IOException, InterruptedException {
        Conversor.eleccionUserMenu();
    }

    public static double obtenerTasa(String urlFinal) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlFinal))
                .GET()
                .build();

        HttpResponse<String> respuesta = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (respuesta.statusCode() != 200) {
            throw new RuntimeException("HTTP " + respuesta.statusCode() + " al consultar ExchangeRate-API");
        }

        JsonElement elemento = JsonParser.parseString(respuesta.body());
        JsonObject objectRoot = elemento.getAsJsonObject();

        String result = objectRoot.get("result").getAsString();
        if (!"success".equalsIgnoreCase(result)) {
            String errorType = objectRoot.has("error-type") ? objectRoot.get("error-type").getAsString() : "desconocido";
            throw new RuntimeException("ExchangeRate-API devolvio error: " + errorType);
        }

        return objectRoot.get("conversion_rate").getAsDouble();
    }
}
