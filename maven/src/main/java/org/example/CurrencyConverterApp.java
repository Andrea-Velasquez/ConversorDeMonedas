package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CurrencyConverterApp {
    private static final String API_KEY = "3bda67f52404c42595cce730";
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Bienvenido al Conversor de Monedas");
            System.out.println("1. Convertir de Dólar a Peso Argentino");
            System.out.println("2. Convertir de Peso Argentino a Dólar");
            System.out.println("3. Convertir de Dólar a Real Brasileño");
            System.out.println("4. Convertir de Real Brasileño a Dólar");
            System.out.println("5. Convertir de Dólar a Peso Colombiano");
            System.out.println("6. Convertir de Peso Colombiano a Dólar");
            System.out.println("7. Salir");
            System.out.print("Elige una opción: ");

            int option = scanner.nextInt();
            if (option == 7) {
                break;
            }

            System.out.print("Ingresa el valor que deseas convertir: ");
            double amount = scanner.nextDouble();

            String fromCurrency = null, toCurrency = null;

            switch (option) {
                case 1:
                    fromCurrency = "USD";
                    toCurrency = "ARS";
                    break;
                case 2:
                    fromCurrency = "ARS";
                    toCurrency = "USD";
                    break;
                case 3:
                    fromCurrency = "USD";
                    toCurrency = "BRL";
                    break;
                case 4:
                    fromCurrency = "BRL";
                    toCurrency = "USD";
                    break;
                case 5:
                    fromCurrency = "USD";
                    toCurrency = "COP";
                    break;
                case 6:
                    fromCurrency = "COP";
                    toCurrency = "USD";
                    break;
                default:
                    System.out.println("Opción no válida");
                    continue;
            }

            double convertedAmount = convertCurrency(fromCurrency, toCurrency, amount);
            System.out.printf("El valor ingresado %.2f %s corresponde a %.2f %s%n", amount, fromCurrency, convertedAmount, toCurrency);
        }

        scanner.close();
    }

    private static double convertCurrency(String fromCurrency, String toCurrency, double amount) {
        String url = API_URL + API_KEY + "/latest/" + fromCurrency;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();
            JsonObject conversionRates = jsonResponse.getAsJsonObject("conversion_rates");
            double rate = conversionRates.get(toCurrency).getAsDouble();
            return amount * rate;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
    }
}