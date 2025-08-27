# Projeto Previsão do Tempo Java

Este projeto é uma aplicação Java para consultar a previsão do tempo de uma cidade utilizando a API OpenWeatherMap.

---

## O que foi feito até agora

- Criação da classe `WeatherApiClient` para fazer requisições HTTP na API externa.
- Implementação da codificaçao do nome da cidade para URL para evitar erros.
- Tratamento do status HTTP para garantir que só parseia JSON em respostas válidas.
- Utilização da biblioteca **Gson** para fazer o parsing dos dados JSON e extrair informações como temperatura, descrição do tempo, umidade e velocidade do vento.
- Configuração do ambiente VS Code para Java, incluindo criação do arquivo `.vscode/settings.json` para referenciar o JAR do Gson e evitar erros de importação.
- Orientação para compilar e executar o programa incluindo o JAR do Gson no classpath.
- Uso correto do Git para versionar o projeto e sincronizar com o repositório remoto no GitHub, incluindo resolução do erro de push usando `git pull --rebase` antes do push.

---

## Como executar o programa

1. Configure sua chave da API OpenWeatherMap no arquivo `WeatherApiClient.java` na variável `API_KEY`.
2. Baixe o arquivo `gson-2.10.1.jar` e coloque na pasta raiz do projeto ou em uma pasta `lib`.
3. Compile o programa com:

javac -cp ".;gson-2.10.1.jar" WeatherApiClient.java

text

4. Execute com:

java -cp ".;gson-2.10.1.jar" WeatherApiClient

text

5. No VS Code, a extensão Java reconhecerá o Gson automaticamente pela configuração em `.vscode/settings.json`.

---

## Configuração do ambiente VS Code

- Criar arquivo `.vscode/settings.json` com o seguinte conteúdo:

{
"java.project.referencedLibraries": [
"lib/**/*.jar",
"gson-2.10.1.jar"
]
}

text

- Reinicie o VS Code após criar/alterar este arquivo para aplicar as configurações.

---

## Próximos passos

- Tornar o programa interativo, recebendo o nome da cidade via entrada do usuário.
- Melhorar estrutura do código para tratamento de erros mais robusto.
- Criar testes automatizados.
- Explorar outras funcionalidades da API OpenWeatherMap.

---

## Autor

Gustavo Mimoso - gustavomimoso@outlook.com

---

## Licença

Projeto sob licença MIT - veja arquivo LICENSE para detalhes.
