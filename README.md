# Projeto Previsão do Tempo Java

Este projeto é uma aplicação Java para consultar a previsão do tempo de uma cidade via API OpenWeatherMap, exibindo os dados no console. Ideal para aprender requisições HTTP, manipulação JSON e integração com APIs.

---

## Funcionalidades

- Consulta interativa: usuário digita o nome da cidade várias vezes, até digitar "sair" para encerrar.
- Parsing da resposta JSON utilizando Gson.
- Exibição de dados principais: nome da cidade, temperatura, descrição do tempo, umidade e velocidade do vento.
- Tratamento de erros da API.
- Configuração da biblioteca Gson no ambiente VS Code.
- Comandos para compilar e executar com o JAR do Gson.
- Uso do Git para versionamento e atualização no GitHub.

---

## Como executar

1. Tenha o Java JDK (11 ou superior) instalado.
2. Baixe `gson-2.10.1.jar` e coloque na pasta raiz do projeto ou em `lib/`.
3. Configure `.vscode/settings.json` com:

{
"java.project.referencedLibraries": [
"lib/**/*.jar",
"gson-2.10.1.jar"
]
}

text

4. Insira sua chave de API no `WeatherApiClient.java`:

private static final String API_KEY = "SUA_CHAVE_API_AQUI";

text

5. Compile:

javac -cp ".;gson-2.10.1.jar" WeatherApiClient.java

text

6. Execute e digite o nome das cidades que desejar:

java -cp ".;gson-2.10.1.jar" WeatherApiClient

text

Digite `sair` para encerrar o programa.

---

## Exemplo de uso

Digite o nome da cidade (ou 'sair' para encerrar): Rio de Janeiro
Status HTTP: 200
Cidade: Rio de Janeiro
Temperatura: 25.5°C
Descrição do tempo: céu limpo
Umidade: 68%
Velocidade do vento: 5.1 m/s

Digite o nome da cidade (ou 'sair' para encerrar): sair
Finalizando o programa!

text

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

## Próximos passos

- Melhorar tratamento de erros e validação de entrada.
- Adicionar previsão para vários dias.
- Implementar testes unitários.
- Modularizar o código.

---

## Autor

Gustavo Mimoso

---

## Licença

Projeto sob licença MIT. Consulte o arquivo LICENSE para detalhes.
