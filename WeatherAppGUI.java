import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.*;
import java.nio.charset.StandardCharsets;
import com.google.gson.*;

public class WeatherAppGUI extends JFrame {

    private JTextField cityField, latField, lonField;
    private JTextArea resultArea;
    private JButton btnConsultarCity, btnConsultarCoord;
    private static final String API_KEY ="9baeee197ed91d5ebd1309a425f13eb5";

    public WeatherAppGUI() {
        super("Previsão do Tempo - Java GUI");

        cityField = new JTextField(15);
        latField = new JTextField(8);
        lonField = new JTextField(8);
        btnConsultarCity = new JButton("Consultar Cidade");
        btnConsultarCoord = new JButton("Consultar Coordenadas");
        resultArea = new JTextArea(22, 46);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Cidade: "));
        topPanel.add(cityField);
        topPanel.add(btnConsultarCity);

        JPanel coordPanel = new JPanel();
        coordPanel.add(new JLabel("Latitude:"));
        coordPanel.add(latField);
        coordPanel.add(new JLabel("Longitude:"));
        coordPanel.add(lonField);
        coordPanel.add(btnConsultarCoord);

        JPanel inputPanel = new JPanel(new GridLayout(2, 1));
        inputPanel.add(topPanel);
        inputPanel.add(coordPanel);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        //Botão para consulta por cidade
        btnConsultarCity.addActionListener(e -> {
            String city = cityField.getText().trim();
            if (city.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Digite o nome da cidade", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            consultarPorCidade(city);
        });

        //Botão para consulta por coordenadas
        btnConsultarCoord.addActionListener(e ->{
            try{
                double lat = Double.parseDouble(latField.getText().trim());
                double lon = Double.parseDouble(lonField.getText().trim());
                consultarPorCoordenadas(lat, lon);
            } catch (NumberFormatException ex){
            JOptionPane.showMessageDialog(this, "Latitude/longitude inválida!", "Erro", JOptionPane.ERROR_MESSAGE);    
            }
        });
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null); //Centraliza na tela
        setVisible(true);
    }
    //Consulta por nome da cidade
    private void consultarPorCidade(String city) {
        resultArea.setText("Consultnado dados para: " + city + "...\n");
        try{
            HttpClient client = HttpClient.newHttpClient();
            Gson gson = new Gson();
            String encodedCity = URLEncoder.encode(city,StandardCharsets.UTF_8);
            URI uriCurrent = new URI("https", "api.openweathermap.org","/data/2.5/weather","q=" + encodedCity + "&units=metric&lang=pt_br&appid=" + API_KEY, null);
            processarConsulta(uriCurrent, client, gson);
        } catch (Exception ex){
            resultArea.append("Erro: " + ex.getMessage() + "\n");
            ex.printStackTrace();
        }
    }
    //Consulta por coordenadas
    private void consultarPorCoordenadas(double lat, double lon){
        resultArea.setText(String.format("Consultando dados para: lat=%.4f, lon=%4.f ...\n", lat, lon));
        try{
            HttpClient client = HttpClient.newHttpClient();
            Gson gson = new Gson();
            URI uriCurrent = new URI("https", "api.openweathermap.org", "/data/2.5/weather","lat=" + lat + "&lon" + lon + "&units=metric&lang=pt_br&appid=" + API_KEY, null);
            processarConsulta(uriCurrent, client, gson);
        } catch (Exception ex){
            resultArea.append("Erro: " + ex.getMessage() + "\n");
            ex.printStackTrace();
        }
    }
      private void processarConsulta(URI uriCurrent, HttpClient client, Gson gson) {
        try {
            HttpRequest reqCurrent = HttpRequest.newBuilder().uri(uriCurrent).build();
            HttpResponse<String> respCurrent = client.send(reqCurrent, HttpResponse.BodyHandlers.ofString());

            if (respCurrent.statusCode() != 200) {
                resultArea.append("Erro ao obter dados atuais: " + respCurrent.body() + "\n");
                return;
            }

            JsonObject currentJson = gson.fromJson(respCurrent.body(), JsonObject.class);

            String cityName = currentJson.get("name").getAsString();
            JsonObject main = currentJson.getAsJsonObject("main");
            double temp = main.get("temp").getAsDouble();
            int humidity = main.get("humidity").getAsInt();
            String description = currentJson.getAsJsonArray("weather").get(0).getAsJsonObject().get("description").getAsString();

            JsonObject sys = currentJson.getAsJsonObject("sys");
            long sunrise = sys.get("sunrise").getAsLong() * 1000;
            long sunset = sys.get("sunset").getAsLong() * 1000;

            resultArea.append("Clima atual em " + cityName + ":\n");
            resultArea.append(String.format("Temperatura: %.1f°C\n", temp));
            resultArea.append("Descrição: " + description + "\n");
            resultArea.append("Umidade: " + humidity + "%\n");
            resultArea.append("Nascer do sol: " + new java.util.Date(sunrise) + "\n");
            resultArea.append("Pôr do sol: " + new java.util.Date(sunset) + "\n");

            JsonObject coord = currentJson.getAsJsonObject("coord");
            double latCoord = coord.get("lat").getAsDouble();
            double lonCoord = coord.get("lon").getAsDouble();

            String forecastParams = String.format("lat=%.4f&lon=%.4f&units=metric&lang=pt_br&appid=%s",
                    latCoord, lonCoord, API_KEY);
            URI uriForecast = new URI("https", "api.openweathermap.org", "/data/2.5/forecast", forecastParams, null);
            HttpRequest reqForecast = HttpRequest.newBuilder().uri(uriForecast).build();
            HttpResponse<String> respForecast = client.send(reqForecast, HttpResponse.BodyHandlers.ofString());

            if (respForecast.statusCode() != 200) {
                resultArea.append("Erro ao obter previsão: " + respForecast.body() + "\n");
                return;
            }

            JsonObject forecastJson = gson.fromJson(respForecast.body(), JsonObject.class);
            JsonArray list = forecastJson.getAsJsonArray("list");

            resultArea.append("\nPrevisão para os próximos dias (a cada 3 horas):\n");
            for (int i = 0; i < Math.min(8, list.size()); i++) {
                JsonObject entry = list.get(i).getAsJsonObject();
                String dt_txt = entry.get("dt_txt").getAsString();

                JsonObject entryMain = entry.getAsJsonObject("main");
                double entryTemp = entryMain.get("temp").getAsDouble();
                int entryHumidity = entryMain.get("humidity").getAsInt();

                JsonObject weatherEntry = entry.getAsJsonArray("weather").get(0).getAsJsonObject();
                String entryDescription = weatherEntry.get("description").getAsString();

                JsonObject windEntry = entry.getAsJsonObject("wind");
                double windSpeed = windEntry.get("speed").getAsDouble();

                resultArea.append(String.format(
                    "%s - %.1f°C - %s - Umidade: %d%% - Vento: %.1f m/s\n",
                    dt_txt, entryTemp, entryDescription, entryHumidity, windSpeed));
            }
            resultArea.append("\n");

        } catch (Exception ex) {
            resultArea.append("Erro ao processar consulta: " + ex.getMessage() + "\n");
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WeatherAppGUI());
    }
}
