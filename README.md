# Projeto Previsão do Tempo Java

Este projeto é uma aplicação Java que consulta a previsão do tempo de uma cidade utilizando a API OpenWeatherMap, mostrando os dados no console. Projeto ideal para iniciantes aprenderem sobre requisições HTTP, manipulação de JSON e integração com APIs externas.

---

## Funcionalidades

- Requisição dos dados de clima pelo nome da cidade digitada pelo usuário.
- Parsing da resposta JSON usando a biblioteca Gson.
- Exibição dos dados principais: nome da cidade, temperatura, descrição do tempo, umidade, velocidade do vento.
- Tratamento de erros da API (exibe mensagem em caso de cidade inválida ou problemas de conexão).
- Configuração facilitada do ambiente de desenvolvimento no VS Code para reconhecer bibliotecas externas.
- Projeto pronto para compilar e executar via linha de comando ou terminal integrado.

---

## Como executar

1. Instale o Java JDK (versão 11 ou superior) na máquina.
2. Faça download do arquivo `gson-2.10.1.jar` (biblioteca Gson) e coloque na pasta raiz do projeto ou em `lib/`.
3. Garanta que existe o arquivo `.vscode/settings.json` com o seguinte conteúdo:

    ```
    {
      "java.project.referencedLibraries": [
        "lib/**/*.jar",
        "gson-2.10.1.jar"
      ]
    }
    ```

4. No arquivo `WeatherApiClient.java`, insira sua chave de API em `private static final String API_KEY = "SUA_CHAVE_API_AQUI";`
5. Compile o projeto pelo terminal integrado ou CMD na pasta do projeto:

    ```
    javac -cp ".;gson-2.10.1.jar" WeatherApiClient.java
    ```

6. Rode o programa e digite o nome da cidade quando solicitado:

    ```
    java -cp ".;gson-2.10.1.jar" WeatherApiClient
    ```

---

## Exemplo de uso

Digite o nome da cidade: Salvador
Status HTTP: 200
Resposta JSON completa: {"coord": ... }
Cidade: Salvador
Temperatura: 26.45°C
Descrição do tempo: céu limpo
Umidade: 79%
Velocidade do vento: 4.5 m/s

text

---

## Estrutura do projeto

PrevisaoDoTempoJava/
├── WeatherApiClient.java
├── gson-2.10.1.jar
└── .vscode/
└── settings.json

text

---

## Histórico de atualizações

- Projeto iniciado com .java e conexão HTTP.
- Inclusão do parsing JSON com Gson e configuração do VS Code.
- Programa agora interativo: usuário digita o nome da cidade.
- Todos os erros anteriores corrigidos e README.md atualizado.
- Orientação para comandos básicos do Git: add, commit, push e resolução de conflitos.

---

## Próximos passos

- Aprimorar tratamento de erros (mensagens customizadas para problemas de conexão e cidade não encontrada).
- Permitir múltiplas consultas sem fechar o programa.
- Adicionar testes automatizados e modularização do código.
- Explorar recursos extras da API, como previsão de vários dias.

---

## Autor

Gustavo Mimoso

---

## Licença

Projeto sob licença MIT. Consulte o arquivo LICENSE para detalhes.
