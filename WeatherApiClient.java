import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class WeatherApiClient {

    private static final String API_KEY = "9baeee197ed91d5ebd1309a425f13eb5";

    public static void main(String[] args) {
        try {
            String city = "Sao Paulo";
            String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8);

            URI uri = new URI("https", "api.openweathermap.org", "/data/2.5/weather",
                    "q=" + encodedCity + "&units=metric&lang=pt_br&appid=" + API_KEY, null);
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Status HTTP: " + response.statusCode());
            System.out.println("Resposta JSON completa: " + response.body());

            if(response.statusCode() == 200) {
                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);

                String cityName = jsonObject.get("name").getAsString();
                double temperature = jsonObject.getAsJsonObject("main").get("temp").getAsDouble();
                String weatherDescription = jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject().get("description").getAsString();
                int humidity = jsonObject.getAsJsonObject("main").get("humidity").getAsInt();
                double windSpeed = jsonObject.getAsJsonObject("wind").get("speed").getAsDouble();

                System.out.println("Cidade: " + cityName);
                System.out.println("Temperatura: " + temperature + "°C");
                System.out.println("Descrição do tempo: " + weatherDescription);
                System.out.println("Umidade: " + humidity + "%");
                System.out.println("Velocidade do vento: " + windSpeed + " m/s");
            } else {
                System.out.println("Erro ao obter dados da API: " + response.body());
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
