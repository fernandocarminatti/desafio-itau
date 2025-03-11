# # desafio-itau-backend

## Descrição

O desafio consiste em criar um sistema de transações financeiras, onde o usuário pode inserir transações e consultar estatísticas sobre as transações feitas.
[Leia o README Desafio](./README_Desafio.md)

## Tecnologias

- Java 17
- Maven
- Docker
- Docker Compose

## Clonar e executar o projeto
Certifique-se de que as Tecnologias estão instaladas no seu ambiente.

Clone o projeto usando o comando:
```bash
git clone https://github.com/frnd/desafio-itau-backend.git
```
"Empacote" o projeto usando maven com o comando:

```bash
mvn clean package
```
- Um arquivo .jar será gerado na pasta ./target com o nome ChallengeApp-1.0.jar.

Caso tenha sucesso na criação do .jar, basta rodar o seguinte comando:

```bash
docker-compose up -d
```
 - -d significa detached. Após o comando os containers vão ser iniciados e não vão estar atrelados ao shell onde o comando foi usado.

Docker compose vai gerenciar a criação dos containers bem como o banco de dados e usuário de acesso.
Tabelas serão criadas automaticamente.
- Transações serão armazenadas em uma tabela chamada `tb_transaction`.
- Estatísticas serão armazenadas em uma tabela chamada `tb_statistic`.

Para parar a aplicação basta rodar o comando:
```bash
docker-compose down
```

## Endpoints - LOCALHOST:8080

### POST /transactions/transacao
Cria uma nova transação
- Valor em decimal com ponto flutuante maior do que zero.
- **Data/Hora no padrão ISO 8601** em que a transação aconteceu
#### Request
```json
{
  "valor": 100.00,
  "dataHora": "2020-08-07T12:34:56.789-03:00"
}
```
#### Response
- `201 Created` sem nenhum corpo
  - A transação foi aceita (ou seja foi validada, está válida e foi registrada)
- `422 Unprocessable Entity` sem nenhum corpo
  - A transação **não** foi aceita por qualquer motivo (1 ou mais dos critérios de aceite não foram atendidos - por exemplo: uma transação com valor menor que `0`)
- `400 Bad Request` sem nenhum corpo
  - A API não compreendeu a requisição do cliente (por exemplo: um JSON inválido)

### GET /statistics/estatisticas
Retorna as estatísticas da transação

#### Request
- Não há parâmetros

#### Response
```json
{
  "count": 1,
  "sum": 100.0,
  "avg": 100.0,
  "min": 100.0,
  "max": 100.0
}
```
- `200 OK` com os dados das estatísticas dos ultimos 60 minutos.
#### Internals
No [DESAFIO](./README_Desafio.md), o sistema não armazena as transações, apenas retorna as estatísticas dos últimos 60 segundos.

Implementado de forma que seja possível consultar estatísticas de um período de tempo mais maleável, por enquanto hardcoded em 60 minutos (1 hora) a partir do timestamp da request.

TODO:
  - Implementar timestamp via request.
  - Implementar timestamp via variáveis de ambiente e com isso implementar endpoint para permitir refresh do application.yaml

### GET /transactions
Retorna todas as transações.
Fora do Escopo do [DESAFIO](./README_Desafio.md), existente para que seja possível visualizar as transações de forma mais simples.

#### Request
Não há parâmetros

#### Response
- Lista de transações:

```json 
{
  "id": "c3dc4956-a1c8-4ae5-9da9-2de364b21d11",
  "amount": 3054.68,
  "timestamp": "2025-03-07T13:34:53.882-03:00"
}
```

### DELETE /transactions/transacao
Apaga todas as transações.

#### Request
Não há parâmetros

#### Response
- `200 OK` sem nenhum corpo
  - Todas as informações foram apagadas com sucesso.