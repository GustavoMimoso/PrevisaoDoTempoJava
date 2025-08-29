import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class WeatherApiClient {

    private static final String API_KEY = "9baeee197ed91d5ebd1309a425f13eb5";

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        HttpClient client = HttpClient.newHttpClient();
        Gson gson = new Gson();

        while (true) {
            System.out.println("Escolha o tipo de consulta:");
            System.out.println("1 - Pelo nome da cidade");
            System.out.println("2 - Por coordenadas (latitude e longitude)");
            System.out.println("Digite 'sair' para encerrar o programa.");
            System.out.print("Opção: ");
            String option = scanner.nextLine().trim();

            if (option.equalsIgnoreCase("sair")) {
                System.out.println("Programa encerrado.");
                break;
            }

            String city = null;
            Double lat = null;
            Double lon = null;

            if (option.equals("1")) {
                System.out.print("Digite o nome da cidade: ");
                city = scanner.nextLine().trim();
                if (city.isEmpty()) {
                    System.out.println("Entrada vazia! Por favor, digite o nome de uma cidade válida.");
                    continue;
                }
            } else if (option.equals("2")) {
                try {
                    System.out.print("Digite a latitude: ");
                    lat = Double.parseDouble(scanner.nextLine().trim());
                    System.out.print("Digite a longitude: ");
                    lon = Double.parseDouble(scanner.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Latitude ou longitude inválidas! Tente novamente.");
                    continue;
                }
            } else {
                System.out.println("Opção inválida, tente novamente.");
                continue;
            }

            try {
                URI uriCurrent;
                if (city != null) {
                    String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8);
                    uriCurrent = new URI("https", "api.openweathermap.org", "/data/2.5/weather",
                            "q=" + encodedCity + "&units=metric&lang=pt_br&appid=" + API_KEY, null);
                } else {
                    uriCurrent = new URI("https", "api.openweathermap.org", "/data/2.5/weather",
                            "lat=" + lat + "&lon=" + lon + "&units=metric&lang=pt_br&appid=" + API_KEY, null);
                }

                HttpRequest reqCurrent = HttpRequest.newBuilder().uri(uriCurrent).build();
                HttpResponse<String> respCurrent = client.send(reqCurrent, HttpResponse.BodyHandlers.ofString());

                if (respCurrent.statusCode() != 200) {
                    System.out.println("Erro ao obter dados atuais: " + respCurrent.body());
                    continue;
                }

                JsonObject currentJson = gson.fromJson(respCurrent.body(), JsonObject.class);

                String cityName = currentJson.get("name").getAsString();
                JsonObject main = currentJson.getAsJsonObject("main");
                double temp = main.get("temp").getAsDouble();
                int humidity = main.get("humidity").getAsInt();
                JsonArray weatherArray = currentJson.getAsJsonArray("weather");
                String description = weatherArray.get(0).getAsJsonObject().get("description").getAsString();

                JsonObject sys = currentJson.getAsJsonObject("sys");
                long sunrise = sys.get("sunrise").getAsLong() * 1000;
                long sunset = sys.get("sunset").getAsLong() * 1000;

                System.out.printf("Clima atual em %s:%n", cityName);
                System.out.printf("Temperatura: %.1f°C%n", temp);
                System.out.printf("Descrição: %s%n", description);
                System.out.printf("Umidade: %d%%%n", humidity);
                System.out.printf("Nascer do sol: %s%n", new java.util.Date(sunrise).toString());
                System.out.printf("Pôr do sol: %s%n", new java.util.Date(sunset).toString());

                JsonObject coord = currentJson.getAsJsonObject("coord");
                double latCoord = coord.get("lat").getAsDouble();
                double lonCoord = coord.get("lon").getAsDouble();

                String forecastParams = String.format("lat=%.4f&lon=%.4f&units=metric&lang=pt_br&appid=%s",
                        latCoord, lonCoord, API_KEY);
                URI uriForecast = new URI("https", "api.openweathermap.org", "/data/2.5/forecast", forecastParams, null);
                HttpRequest reqForecast = HttpRequest.newBuilder().uri(uriForecast).build();
                HttpResponse<String> respForecast = client.send(reqForecast, HttpResponse.BodyHandlers.ofString());

                if (respForecast.statusCode() != 200) {
                    System.out.println("Erro ao obter previsão: " + respForecast.body());
                    continue;
                }

                JsonObject forecastJson = gson.fromJson(respForecast.body(), JsonObject.class);
                JsonArray list = forecastJson.getAsJsonArray("list");

                System.out.println("\nPrevisão para os próximos dias (a cada 3 horas):");

                for (int i = 0; i < list.size(); i++) {
                    JsonObject entry = list.get(i).getAsJsonObject();
                    String dt_txt = entry.get("dt_txt").getAsString();

                    JsonObject entryMain = entry.getAsJsonObject("main");
                    double entryTemp = entryMain.get("temp").getAsDouble();
                    int entryHumidity = entryMain.get("humidity").getAsInt();

                    JsonObject weatherEntry = entry.getAsJsonArray("weather").get(0).getAsJsonObject();
                    String entryDescription = weatherEntry.get("description").getAsString();

                    JsonObject windEntry = entry.getAsJsonObject("wind");
                    double windSpeed = windEntry.get("speed").getAsDouble();

                    System.out.printf("%s - %.1f°C - %s - Umidade: %d%% - Vento: %.1f m/s%n",
                            dt_txt, entryTemp, entryDescription, entryHumidity, windSpeed);

                    // Mostrar só os próximos ~24h (8 blocos)
                    if (i == 7) break;
                }

                System.out.println();

            } catch (Exception e) {
                System.out.println("Erro ao obter dados: " + e.getMessage());
                e.printStackTrace();
            }
        }

        scanner.close();
    }
}
