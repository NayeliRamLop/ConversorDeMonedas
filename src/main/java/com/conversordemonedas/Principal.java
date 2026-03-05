package com.conversordemonedas;

import com.conversordemonedas.modelo.RespuestaCambio;
import com.conversordemonedas.servicio.ConvertidorMoneda;
import com.conversordemonedas.servicio.ExchangeRateApiCliente;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class Principal {
    private static final String DEFAULT_API_KEY = "cb1261189e0011be74fd085a";
    private static final int OPCION_SALIR = 7;
    private static final Map<Integer, OpcionConversion> OPCIONES = new LinkedHashMap<>();

    static {
        OPCIONES.put(1, new OpcionConversion("USD", "ARS", "Dolar =>> Peso argentino"));
        OPCIONES.put(2, new OpcionConversion("ARS", "USD", "Peso argentino =>> Dolar"));
        OPCIONES.put(3, new OpcionConversion("USD", "BRL", "Dolar =>> Real brasileno"));
        OPCIONES.put(4, new OpcionConversion("BRL", "USD", "Real brasileno =>> Dolar"));
        OPCIONES.put(5, new OpcionConversion("USD", "COP", "Dolar =>> Peso colombiano"));
        OPCIONES.put(6, new OpcionConversion("COP", "USD", "Peso colombiano =>> Dolar"));
    }

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Gson gson = new Gson();
        ExchangeRateApiCliente apiCliente = new ExchangeRateApiCliente(gson);
        ConvertidorMoneda convertidor = new ConvertidorMoneda(apiCliente);

        String apiKey = System.getenv("EXCHANGE_RATE_API_KEY");
        if (apiKey == null || apiKey.isBlank()) {
            apiKey = DEFAULT_API_KEY;
        }

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                mostrarMenu();
                int opcion = leerOpcionMenu(scanner);

                if (opcion == OPCION_SALIR) {
                    System.out.println("Gracias por usar el conversor.");
                    break;
                }

                OpcionConversion seleccion = OPCIONES.get(opcion);
                if (seleccion == null) {
                    System.out.println("Elija una opcion valida.");
                    continue;
                }

                double monto = leerMontoValido(scanner);
                RespuestaCambio respuesta = convertidor.convertir(
                        monto,
                        seleccion.monedaOrigen(),
                        seleccion.monedaDestino(),
                        apiKey
                );

                System.out.println("\nResultado de conversion:");
                System.out.printf("%.2f %s = %.2f %s%n",
                        respuesta.montoOriginal(),
                        respuesta.monedaOrigen(),
                        respuesta.montoConvertido(),
                        respuesta.monedaDestino());
                System.out.println("Tasa usada: " + respuesta.tasaCambio());
                System.out.println();
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Error consultando la API: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("No se pudo completar la conversion: " + e.getMessage());
        }
    }

    private static void mostrarMenu() {
        System.out.println("***********************************************");
        System.out.println("Sea bienvenido/a al Conversor de Moneda =]");
        System.out.println();
        for (Map.Entry<Integer, OpcionConversion> entry : OPCIONES.entrySet()) {
            System.out.printf("%d) %s%n", entry.getKey(), entry.getValue().descripcion());
        }
        System.out.println(OPCION_SALIR + ") Salir");
        System.out.println("Elija una opcion valida:");
        System.out.println("***********************************************");
    }

    private static int leerOpcionMenu(Scanner scanner) {
        while (true) {
            String entrada = scanner.nextLine().trim();
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Opcion invalida. Ingresa un numero del menu.");
            }
        }
    }

    private static double leerMontoValido(Scanner scanner) {
        while (true) {
            System.out.print("Monto a convertir: ");
            String entrada = scanner.nextLine().trim();
            try {
                double monto = Double.parseDouble(entrada);
                if (monto > 0) {
                    return monto;
                }
                System.out.println("El monto debe ser mayor que cero.");
            } catch (NumberFormatException e) {
                System.out.println("Monto invalido. Ingresa un numero, por ejemplo 100.50");
            }
        }
    }

    private record OpcionConversion(String monedaOrigen, String monedaDestino, String descripcion) {
    }
}
