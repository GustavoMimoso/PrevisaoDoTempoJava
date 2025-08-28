# Projeto Previsão do Tempo Java

Aplicação Java para consultar e exibir a previsão do tempo usando a API OpenWeatherMap.

---

## Funcionalidades

- Consulta interativa por cidade, com opção de encerrar digitando "sair".
- Exibição do clima atual com temperatura, descrição, umidade, velocidade do vento.
- Exibição do nascer e pôr do sol na cidade consultada.
- Previsão detalhada para os próximos 5 dias, a cada 3 horas.
- Tratamento de erros da API e entrada do usuário.
- Configuração do ambiente VS Code para trabalhar com Gson.
- Código pronto para compilação e execução via linha de comando.

---

## Como configurar o ambiente

1. Instale o Java JDK 11 ou superior.
2. Baixe o arquivo `gson-2.10.1.jar` e coloque na raiz do projeto ou dentro da pasta `lib`.
3. Crie o arquivo `.vscode/settings.json` com o conteúdo:

{
"java.project.referencedLibraries": [
"lib/**/*.jar",
"gson-2.10.1.jar"
]
}

text

---

## Como compilar e executar

1. No terminal, compile:

javac -cp ".;gson-2.10.1.jar" WeatherApiClient.java

text

2. Execute:

java -cp ".;gson-2.10.1.jar" WeatherApiClient

text

Digite uma cidade para consultar o clima e previsão, ou digite `sair` para encerrar.

---

## Estrutura do projeto

PrevisaoDoTempoJava/
├── WeatherApiClient.java
├── gson-2.10.1.jar
├── README.md
└── .vscode/
└── settings.json

text

---

## Exemplos de uso

Digite o nome da cidade (ou 'sair' para encerrar): São Paulo
Clima atual em São Paulo:
Temperatura: 19.7°C
Descrição: poucas nuvens
Umidade: 81%
Nascer do sol: Thu Aug 28 06:02:06 BRT 2025
Pôr do sol: Thu Aug 28 17:33:16 BRT 2025

Previsão para os próximos dias (a cada 3 horas):
2025-08-28 15:00:00 - 20.0°C - céu claro - Umidade: 80% - Vento: 3.5 m/s
2025-08-28 18:00:00 - 18.5°C - céu claro - Umidade: 78% - Vento: 2.8 m/s
...

Digite o nome da cidade (ou 'sair' para encerrar):

text

---

## Próximos passos

- Adicionar suporte para busca por coordenadas geográficas.
- Criar interface gráfica simples.
- Melhorar tratamento e logs de erro.
- Adicionar testes unitários.

---

## Autor

Gustavo Mimoso

---

## Licença

Licenciado sob MIT. Consulte LICENSE para mais informações.
