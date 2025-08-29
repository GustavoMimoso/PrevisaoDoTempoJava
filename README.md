# Projeto Previsão do Tempo Java

Aplicação Java para consultar e exibir a previsão do tempo usando a API OpenWeatherMap.

---

## Funcionalidades

- Interface gráfica Swing com:
  - Pesquisa por nome da cidade.
  - Pesquisa por coordenadas (latitude e longitude).
  - Exibição do clima atual com temperatura, descrição, umidade, velocidade do vento.
  - Exibição de nascer e pôr do sol.
  - Previsão detalhada para os próximos dias (a cada 3 horas).
- Tratamento de erros amigável na interface.
- Código modularizado para fácil manutenção.
- Uso da biblioteca Gson para parsing JSON.
- Pronto para rodar via linha de comando com JAR do Gson incluído.

---

## Como configurar o ambiente

1. Instale Java JDK 11 ou superior.
2. Baixe `gson-2.10.1.jar` e coloque na raiz do projeto ou em `lib/`.
3. Configure `.vscode/settings.json` para referenciar o JAR Gson:

{
"java.project.referencedLibraries": [
"lib/**/*.jar",
"gson-2.10.1.jar"
]
}

text

---

## Como compilar e executar

1. Compile a interface gráfica:

javac -cp ".;gson-2.10.1.jar" WeatherAppGUI.java

text

2. Execute:

java -cp ".;gson-2.10.1.jar" WeatherAppGUI

text

---

## Estrutura do projeto

PrevisaoDoTempoJava/
├── WeatherApiClient.java # Código console tradicional (opcional)
├── WeatherAppGUI.java # Interface gráfica Swing principal
├── gson-2.10.1.jar # Biblioteca Gson para JSON parsing
├── README.md
└── .vscode/
└── settings.json

text

---

## Exemplos de uso

- Digite o nome de uma cidade e clique em "Consultar Cidade" para obter previsão.
- Ou informe latitude e longitude e clique em "Consultar Coordenadas".
- O resultado aparecerá na área de texto abaixo dos campos.

---

## Próximos passos

- Adicionar logs detalhados e tratamento avançado de exceções.
- Criar testes unitários para os métodos de consulta.
- Explorar melhorias de UI como tema escuro, seleção de unidades, etc.
- Adicionar suporte para previsão estendida semanal via API One Call.

---

## Autor

Gustavo Mimoso
