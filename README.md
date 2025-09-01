# Projeto Previsão do Tempo Java

Aplicação Java com interface gráfica Swing para consultar e exibir a previsão do tempo usando a API OpenWeatherMap.

---

## Funcionalidades

- Interface gráfica moderna com FlatLaf (versão 3.6)  
- Tema escuro/claro dinamicamente alternável  
- Pesquisa por nome da cidade  
- Pesquisa por coordenadas (latitude e longitude)  
- Seleção de unidades: Celsius, Fahrenheit, Kelvin  
- Exibição do clima atual: temperatura, descrição, umidade, nascer e pôr do sol  
- Previsão estendida para os próximos 7 dias via One Call API  
- Logging detalhado em console e arquivo (`weather_app.log`)  
- Requisições em segundo plano com SwingWorker  
- Tratamento avançado de exceções  

---

## Estrutura do projeto

PrevisaoDoTempoJava/
├── WeatherAppGUI.java # Interface gráfica principal
├── gson-2.10.1.jar # Biblioteca Gson para JSON parsing
├── flatlaf-3.6.jar # FlatLaf Look and Feel
├── README.md
└── .vscode/
└── settings.json

text

---

## Como configurar o ambiente

1. Instale Java JDK 11 ou superior.  
2. Baixe as dependências e coloque na pasta do projeto:  
   - `gson-2.10.1.jar`  
   - `flatlaf-3.6.jar`  
3. Configure o VS Code em `.vscode/settings.json`:

{
"java.project.referencedLibraries": [
"gson-2.10.1.jar",
"flatlaf-3.6.jar"
]
}

text

---

## Como compilar e executar

No **Windows**:

javac -cp ".;gson-2.10.1.jar;flatlaf-3.6.jar" WeatherAppGUI.java
java -cp ".;gson-2.10.1.jar;flatlaf-3.6.jar" WeatherAppGUI

text

No **Linux/Mac**:

javac -cp ".:gson-2.10.1.jar:flatlaf-3.6.jar" WeatherAppGUI.java
java -cp ".:gson-2.10.1.jar:flatlaf-3.6.jar" WeatherAppGUI

text

---

## Exemplos de uso

1. Execute o programa para abrir a janela gráfica.  
2. Digite uma cidade e clique em **Consultar Cidade** ou informe coordenadas e clique em **Consultar Coordenadas**.  
3. Use o menu **Unidades** para alternar entre °C, °F ou K.  
4. Clique no botão de tema para alternar entre modo escuro e claro.  
5. Veja o clima atual e a previsão estendida de 7 dias no painel abaixo.  

---

## Próximos passos

- Criar testes unitários (JUnit) para métodos de consulta.  
- Adicionar logs de métricas de desempenho.  
- Melhorar UI com ícones e responsividade.  
- Implementar cache local para reduzir chamadas à API.  

---

## Autor

Gustavo Mimoso
