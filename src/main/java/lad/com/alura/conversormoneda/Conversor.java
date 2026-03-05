package lad.com.alura.conversormoneda;

import java.io.IOException;
import java.util.Scanner;

public class Conversor {
    private static final String API_BASE = "https://v6.exchangerate-api.com/v6";

    public static void eleccionUserMenu() throws IOException, InterruptedException {
        String apiKey = System.getenv("EXCHANGE_RATE_API_KEY");
        if (apiKey == null || apiKey.isBlank()) {
            System.out.println("Configura EXCHANGE_RATE_API_KEY antes de ejecutar.");
            System.out.println("PowerShell: $env:EXCHANGE_RATE_API_KEY='tu-clave'");
            return;
        }

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Moneda origen (ej. USD): ");
            String monedaOrigen = scanner.nextLine().trim().toUpperCase();

            System.out.print("Moneda destino (ej. MXN): ");
            String monedaDestino = scanner.nextLine().trim().toUpperCase();

            System.out.print("Monto a convertir: ");
            double monto = Double.parseDouble(scanner.nextLine().trim());

            String urlFinal = API_BASE + "/" + apiKey + "/pair/" + monedaOrigen + "/" + monedaDestino;
            double tasa = ConversorApp.obtenerTasa(urlFinal);
            double convertido = monto * tasa;

            System.out.printf("%.2f %s = %.2f %s%n", monto, monedaOrigen, convertido, monedaDestino);
            System.out.println("Tasa: " + tasa);
        }
    }
}
