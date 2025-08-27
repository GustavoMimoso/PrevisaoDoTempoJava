# Projeto Previsão do Tempo Java

Este projeto é uma aplicação Java simples que consome a API do OpenWeatherMap para obter a previsão do tempo de uma cidade especificada.

---

## Descrição

Este projeto demonstra como fazer requisições HTTP em Java, processar dados JSON e apresentar informações do clima em tempo real, utilizando a API pública do OpenWeatherMap. É ideal para iniciantes que querem aprender a integrar APIs em Java.

---

## Funcionalidades

- Requisição de dados da API OpenWeatherMap via HTTP GET
- Processamento da resposta JSON (ainda na próxima etapa)
- Mostra as informações básicas do tempo para uma cidade
- Interface simples pelo console

---

## Tecnologias

- Java 11+ (HttpClient)
- VS Code (IDE sugerida)
- API OpenWeatherMap

---

## Como usar

1. Clone ou baixe este repositório.
2. Abra a pasta no VS Code.
3. Instale o Extension Pack for Java no VS Code.
4. Configure o JDK 11 ou superior e defina a variável JAVA_HOME.
5. Obtenha uma chave gratuita no [OpenWeatherMap](https://openweathermap.org/api).
6. No arquivo `WeatherApiClient.java`, substitua `SUA_CHAVE_API_AQUI` pela sua chave.
7. Compile e execute:
javac WeatherApiClient.java
java WeatherApiClient

text

---

## Próximos passos

- Fazer o parsing do JSON e extrair os dados relevantes.
- Desenvolvimento de uma interface gráfica simples.
- Tratar erros e inputs inválidos.

---

## Autor

Gustavo Henrique de Souza Mimoso - gustavomimoso@outlook.com

---

## Licença

Este projeto está sob licença MIT - veja o arquivo LICENSE para detalhes.
