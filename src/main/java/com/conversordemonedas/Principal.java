package com.conversordemonedas;

import com.conversordemonedas.modelo.RespuestaCambio;
import com.conversordemonedas.servicio.ConvertidorMoneda;
import com.conversordemonedas.servicio.ExchangeRateApiCliente;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Gson gson = new Gson();
        ExchangeRateApiCliente apiCliente = new ExchangeRateApiCliente(gson);
        ConvertidorMoneda convertidor = new ConvertidorMoneda(apiCliente);

        String apiKey = System.getenv("EXCHANGE_RATE_API_KEY");
        if (apiKey == null || apiKey.isBlank()) {
            System.out.println("Falta la variable de entorno EXCHANGE_RATE_API_KEY.");
            System.out.println("Ejemplo PowerShell: $env:EXCHANGE_RATE_API_KEY='tu-clave'");
            return;
        }

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Moneda origen (ej. USD): ");
            String monedaOrigen = scanner.nextLine().trim().toUpperCase();

            System.out.print("Moneda destino (ej. MXN): ");
            String monedaDestino = scanner.nextLine().trim().toUpperCase();

            System.out.print("Monto a convertir: ");
            double monto = Double.parseDouble(scanner.nextLine().trim());

            RespuestaCambio respuesta = convertidor.convertir(monto, monedaOrigen, monedaDestino, apiKey);

            System.out.println("\nResultado de conversion:");
            System.out.printf("%.2f %s = %.2f %s%n",
                    respuesta.montoOriginal(),
                    respuesta.monedaOrigen(),
                    respuesta.montoConvertido(),
                    respuesta.monedaDestino());
            System.out.println("Tasa usada: " + respuesta.tasaCambio());
        } catch (IOException | InterruptedException e) {
            System.out.println("Error consultando la API: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("No se pudo completar la conversion: " + e.getMessage());
        }
    }
}
