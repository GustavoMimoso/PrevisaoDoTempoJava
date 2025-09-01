import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.*;
import java.io.IOException;
import com.google.gson.*;
import com.formdev.flatlaf.*;

public class WeatherAppGUI extends JFrame {

    private JTextField cityField, latField, lonField;
    private JTextArea resultArea;
    private JButton btnConsultarCity, btnConsultarCoord, btnToggleTheme;
    private JComboBox<String> unitsCombo;
    private static final String API_KEY = "9baeee197ed91d5ebd1309a425f13eb5";
    private static final Logger logger = Logger.getLogger(WeatherAppGUI.class.getName());
    private boolean isDarkTheme = true;

    public WeatherAppGUI() {
        super("Previsão do Tempo - Java GUI Moderno");
        
        setupLogging();
        setupTheme();
        initializeComponents();
        setupLayout();
        setupEventListeners();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        
        logger.info("Aplicação iniciada com sucesso");
    }

    private void setupLogging() {
        try {
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(Level.ALL);
            consoleHandler.setFormatter(new SimpleFormatter());
            
            FileHandler fileHandler = new FileHandler("weather_app.log", true);
            fileHandler.setLevel(Level.ALL);
            fileHandler.setFormatter(new SimpleFormatter());
            
            logger.addHandler(consoleHandler);
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            System.err.println("Erro ao configurar logging: " + e.getMessage());
        }
    }

    private void setupTheme() {
        try {
            if (isDarkTheme) {
                UIManager.setLookAndFeel(new FlatDarkLaf());
            } else {
                UIManager.setLookAndFeel(new FlatLightLaf());
            }
            UIManager.put("Button.arc", 10);
            UIManager.put("Component.focusWidth", 1);
            UIManager.put("TextComponent.arc", 8);
        } catch (UnsupportedLookAndFeelException e) {
            logger.warning("Erro ao aplicar tema FlatLaf, usando tema padrão: " + e.getMessage());
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                logger.severe("Erro crítico ao configurar tema: " + ex.getMessage());
            }
        }
    }

    private void initializeComponents() {
        cityField = new JTextField(15);
        latField = new JTextField(8);
        lonField = new JTextField(8);
        btnConsultarCity = new JButton("🏙️ Consultar Cidade");
        btnConsultarCoord = new JButton("🌍 Consultar Coordenadas");
        btnToggleTheme = new JButton(isDarkTheme ? "☀️ Tema Claro" : "🌙 Tema Escuro");
        String[] units = {"Celsius (°C)", "Fahrenheit (°F)", "Kelvin (K)"};
        unitsCombo = new JComboBox<>(units);
        resultArea = new JTextArea(25, 50);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Consolas", Font.PLAIN, 12));
    }

    private void setupLayout() {
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel cityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cityPanel.setBorder(BorderFactory.createTitledBorder("Consultar por Cidade"));
        cityPanel.add(new JLabel("Cidade:"));
        cityPanel.add(cityField);
        cityPanel.add(btnConsultarCity);

        JPanel coordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        coordPanel.setBorder(BorderFactory.createTitledBorder("Consultar por Coordenadas"));
        coordPanel.add(new JLabel("Latitude:"));
        coordPanel.add(latField);
        coordPanel.add(new JLabel("Longitude:"));
        coordPanel.add(lonField);
        coordPanel.add(btnConsultarCoord);

        JPanel configPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        configPanel.setBorder(BorderFactory.createTitledBorder("Configurações"));
        configPanel.add(new JLabel("Unidades:"));
        configPanel.add(unitsCombo);
        configPanel.add(btnToggleTheme);

        JPanel inputPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        inputPanel.add(cityPanel);
        inputPanel.add(coordPanel);
        inputPanel.add(configPanel);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void setupEventListeners() {
        btnConsultarCity.addActionListener(_ignored -> {
            String city = cityField.getText().trim();
            if (city.isEmpty()) {
                showError("Digite o nome da cidade.");
                logger.warning("Tentativa de consulta com campo cidade vazio");
                return;
            }
            consultarPorCidade(city);
        });

        btnConsultarCoord.addActionListener(_ignored -> {
            try {
                double lat = Double.parseDouble(latField.getText().trim());
                double lon = Double.parseDouble(lonField.getText().trim());
                if (lat < -90 || lat > 90 || lon < -180 || lon > 180) {
                    showError("Coordenadas inválidas! Latitude: -90 a 90, Longitude: -180 a 180");
                    return;
                }
                consultarPorCoordenadas(lat, lon);
            } catch (NumberFormatException ex) {
                showError("Formato de coordenadas inválido!");
                logger.warning("Formato de coordenadas inválido: " + ex.getMessage());
            }
        });

        btnToggleTheme.addActionListener(_ignored -> toggleTheme());
        cityField.addActionListener(_ignored -> btnConsultarCity.doClick());
    }

    private void toggleTheme() {
        isDarkTheme = !isDarkTheme;
        setupTheme();
        SwingUtilities.updateComponentTreeUI(this);
        btnToggleTheme.setText(isDarkTheme ? "☀️ Tema Claro" : "🌙 Tema Escuro");
        logger.info("Tema alterado para: " + (isDarkTheme ? "Escuro" : "Claro"));
    }

    private String getUnitsParam() {
        switch (unitsCombo.getSelectedIndex()) {
            case 1: return "imperial";
            case 2: return "standard";
            default: return "metric";
        }
    }

    private String getUnitsSymbol() {
        switch (unitsCombo.getSelectedIndex()) {
            case 1: return "°F";
            case 2: return "K";
            default: return "°C";
        }
    }

    private void consultarPorCidade(String city) {
        resultArea.setText("🔍 Consultando dados para: " + city + "...\n");
        logger.info("Iniciando consulta por cidade: " + city);
        new SwingWorker<Void, Void>() {
            protected Void doInBackground() {
                try {
                    HttpClient client = HttpClient.newHttpClient();
                    Gson gson = new Gson();
                    String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8);
                    String unitsParam = getUnitsParam();
                    URI uriCurrent = new URI("https", "api.openweathermap.org", "/data/2.5/weather",
                            "q=" + encodedCity + "&units=" + unitsParam + "&lang=pt_br&appid=" + API_KEY, null);
                    processarConsulta(uriCurrent, client, gson);
                    logger.info("Consulta por cidade concluída com sucesso: " + city);
                } catch (Exception ex) {
                    handleException("Erro na consulta por cidade", ex);
                }
                return null;
            }
        }.execute();
    }

    private void consultarPorCoordenadas(double lat, double lon) {
        resultArea.setText(String.format("🌐 Consultando dados para: lat=%.4f, lon=%.4f ...\n", lat, lon));
        logger.info(String.format("Iniciando consulta por coordenadas: lat=%.4f, lon=%.4f", lat, lon));
        new SwingWorker<Void, Void>() {
            protected Void doInBackground() {
                try {
                    HttpClient client = HttpClient.newHttpClient();
                    Gson gson = new Gson();
                    String unitsParam = getUnitsParam();
                    URI uriCurrent = new URI("https", "api.openweathermap.org", "/data/2.5/weather",
                            "lat=" + lat + "&lon=" + lon + "&units=" + unitsParam + "&lang=pt_br&appid=" + API_KEY, null);
                    processarConsulta(uriCurrent, client, gson);
                    logger.info("Consulta por coordenadas concluída com sucesso");
                } catch (Exception ex) {
                    handleException("Erro na consulta por coordenadas", ex);
                }
                return null;
            }
        }.execute();
    }

    private void processarConsulta(URI uriCurrent, HttpClient client, Gson gson) {
        try {
            HttpRequest reqCurrent = HttpRequest.newBuilder()
                    .uri(uriCurrent)
                    .timeout(java.time.Duration.ofSeconds(10))
                    .build();
            HttpResponse<String> respCurrent = client.send(reqCurrent, HttpResponse.BodyHandlers.ofString());
            if (respCurrent.statusCode() != 200) {
                SwingUtilities.invokeLater(() -> resultArea.append(
                        "❌ Erro HTTP " + respCurrent.statusCode() + ": " + respCurrent.body() + "\n"));
                logger.warning("Erro HTTP: " + respCurrent.statusCode());
                return;
            }
            JsonObject currentJson = gson.fromJson(respCurrent.body(), JsonObject.class);
            String unitsSymbol = getUnitsSymbol();
            SwingUtilities.invokeLater(() -> {
                String cityName = currentJson.get("name").getAsString();
                JsonObject main = currentJson.getAsJsonObject("main");
                double temp = main.get("temp").getAsDouble();
                int humidity = main.get("humidity").getAsInt();
                String description = currentJson.getAsJsonArray("weather")
                        .get(0).getAsJsonObject().get("description").getAsString();
                JsonObject sys = currentJson.getAsJsonObject("sys");
                long sunrise = sys.get("sunrise").getAsLong() * 1000;
                long sunset = sys.get("sunset").getAsLong() * 1000;
                resultArea.append("🏙️ " + cityName + "\n");
                resultArea.append("══════════════════════════════\n");
                resultArea.append(String.format("🌡️ Temperatura: %.1f%s\n", temp, unitsSymbol));
                resultArea.append("📝 Descrição: " + description + "\n");
                resultArea.append("💧 Umidade: " + humidity + "%\n");
                resultArea.append("🌅 Nascer do sol: " + new java.util.Date(sunrise) + "\n");
                resultArea.append("🌇 Pôr do sol: " + new java.util.Date(sunset) + "\n");
                JsonObject coord = currentJson.getAsJsonObject("coord");
                double lat = coord.get("lat").getAsDouble();
                double lon = coord.get("lon").getAsDouble();
                buscarPrevisaoEstendida(lat, lon, client, gson);
            });
        } catch (Exception ex) {
            handleException("Erro ao processar consulta", ex);
        }
    }

    private void buscarPrevisaoEstendida(double lat, double lon, HttpClient client, Gson gson) {
        try {
            String unitsParam = getUnitsParam(), unitsSymbol = getUnitsSymbol();
            URI uriOneCall = new URI("https", "api.openweathermap.org", "/data/2.5/onecall",
                    "lat=" + lat + "&lon=" + lon + "&exclude=minutely,alerts&units=" + unitsParam
                            + "&lang=pt_br&appid=" + API_KEY,
                    null);
            HttpRequest reqOneCall = HttpRequest.newBuilder()
                    .uri(uriOneCall)
                    .timeout(java.time.Duration.ofSeconds(10))
                    .build();
            HttpResponse<String> respOneCall = client.send(reqOneCall, HttpResponse.BodyHandlers.ofString());
            if (respOneCall.statusCode() == 200) {
                JsonObject oneCallJson = gson.fromJson(respOneCall.body(), JsonObject.class);
                JsonArray dailyArray = oneCallJson.getAsJsonArray("daily");
                SwingUtilities.invokeLater(() -> {
                    resultArea.append("\n📅 PREVISÃO PARA OS PRÓXIMOS 7 DIAS:\n");
                    resultArea.append("══════════════════════════════\n");
                    for (int i = 0; i < Math.min(7, dailyArray.size()); i++) {
                        JsonObject day = dailyArray.get(i).getAsJsonObject();
                        long dt = day.get("dt").getAsLong() * 1000;
                        JsonObject temp = day.getAsJsonObject("temp");
                        double max = temp.get("max").getAsDouble();
                        double min = temp.get("min").getAsDouble();
                        String desc = day.getAsJsonArray("weather")
                                .get(0).getAsJsonObject().get("description").getAsString();
                        int humidity = day.get("humidity").getAsInt();
                        java.util.Date date = new java.util.Date(dt);
                        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM - EEE");
                        resultArea.append(String.format("%s: %.1f%s/%.1f%s - %s (💧%d%%)\n",
                                sdf.format(date), max, unitsSymbol, min, unitsSymbol, desc, humidity));
                    }
                    resultArea.append("\n");
                });
                logger.info("Previsão estendida obtida com sucesso");
            } else {
                logger.warning("Erro ao obter previsão estendida: " + respOneCall.statusCode());
            }
        } catch (Exception ex) {
            logger.warning("Erro na previsão estendida: " + ex.getMessage());
        }
    }

    private void handleException(String message, Exception ex) {
        logger.severe(message + ": " + ex.getMessage());
        SwingUtilities.invokeLater(() -> resultArea.append("❌ " + message + ": " + ex.getMessage() + "\n"));
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        try {
            FlatDarkLaf.setup();
        } catch (Exception ex) {
            System.err.println("Erro ao configurar FlatLaf: " + ex.getMessage());
        }
        SwingUtilities.invokeLater(() -> new WeatherAppGUI());
    }
}
