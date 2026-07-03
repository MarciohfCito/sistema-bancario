# Banking System

Sistema Bancário Digital — API REST para gerenciamento de clientes, contas, cartões, PIX, transações e investimentos.

**Stack:** Java 21+ · Spring Boot 4.1.0 · MySQL 8.0 · Flyway · JWT · Swagger/OpenAPI

---

## Sumário

- [Visão Geral](#visão-geral)
- [Pré-requisitos](#pré-requisitos)
- [Configuração do Banco de Dados](#configuração-do-banco-de-dados)
- [Como Executar](#como-executar)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Autenticação](#autenticação)
- [Endpoints da API](#endpoints-da-api)
- [Exemplos de Requisições](#exemplos-de-requisições)
- [Credenciais de Teste](#credenciais-de-teste)
- [Swagger UI](#swagger-ui)

---

## Visão Geral

O sistema expõe uma API REST monolítica com as seguintes funcionalidades:

- **Clientes** — Cadastro de pessoas físicas (PF) e jurídicas (PJ)
- **Contas** — Contas corrente e poupança com saldo e limite
- **Cartões** — Cartões físicos e digitais vinculados a contas
- **Chaves PIX** — Gerenciamento de chaves (CPF, CNPJ, email, telefone, aleatória)
- **Transações** — Transferências, pagamentos e operações PIX
- **Investimentos** — Produtos, carteiras e ordens de compra/venda
- **Relatórios** — Relatórios gerenciais de todas as entidades

---

## Pré-requisitos

- **Java** 21 ou superior (JDK)
- **MySQL** 8.0 ou superior
- **Maven** 3.9+ (ou usar o Maven Wrapper `mvnw` incluído)

Verifique as instalações:

```bash
java -version
mysql --version
mvn -version
```

---

## Configuração do Banco de Dados

1. Acesse o MySQL e crie o banco:

```sql
CREATE DATABASE banking_system;
```

2. O arquivo `src/main/resources/application.properties` já contém as credenciais padrão:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/banking_system?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=vh
spring.datasource.password=12345
```

   Altere usuário/senha conforme seu ambiente local.

---

## Como Executar

### 1. Aplicar as migrations do Flyway

```bash
mvn flyway:migrate
```

Isso cria as 8 tabelas e insere os dados de teste.

### 2. Iniciar a aplicação

```bash
mvn spring-boot:run
```

A aplicação sobe em `http://localhost:8080`.

### 3. Acessar a documentação

```
http://localhost:8080/swagger-ui.html
```

---

## Estrutura do Projeto

```
sistema-bancario/
├── docs/
│   ├── Sistema_Bancario_Especificacao_IA.md
│   └── Technical_Report.md
├── src/
│   ├── main/
│   │   ├── java/br/uema/bd/banking_system/
│   │   │   ├── config/          → OpenApiConfig.java
│   │   │   ├── controller/      → 10 controladores REST
│   │   │   ├── dto/             → 20 DTOs (request/response)
│   │   │   ├── entity/          → 8 entidades JPA
│   │   │   ├── exception/       → Exceções customizadas
│   │   │   ├── repository/      → 8 repositórios Spring Data
│   │   │   ├── security/        → JWT, SecurityConfig, UserDetailsService
│   │   │   ├── service/         → 10 serviços + AuthService
│   │   │   └── BankingSystemApplication.java
│   │   └── resources/
│   │       ├── db/migration/    → V1__initial_schema.sql, V2__seed_data.sql
│   │       └── application.properties
│   └── test/
├── pom.xml
└── README.md
```

---

## Autenticação

O sistema usa **JWT (Bearer Token)** com autenticação stateless.

1. Faça login em `POST /api/auth/login` com documento e senha.
2. A resposta inclui um token JWT (válido por 24h por padrão).
3. Envie o token no header de requisições autenticadas:

```
Authorization: Bearer <seu-token-jwt>
```

### Endpoints públicos

- `POST /api/auth/login` e `POST /api/auth/register`
- `GET /api/clients/**` (consulta de clientes)
- Swagger UI e OpenAPI docs

---

## Endpoints da API

### Autenticação — `/api/auth`

| Método | Rota        | Descrição                  |
|--------|-------------|----------------------------|
| POST   | `/login`    | Autenticar e obter token   |
| POST   | `/register` | Cadastrar novo cliente     |

### Clientes — `/api/clients`

| Método | Rota    | Descrição            |
|--------|---------|----------------------|
| GET    | `/`     | Listar todos         |
| GET    | `/{id}` | Buscar por ID        |
| POST   | `/`     | Criar cliente        |
| PUT    | `/{id}` | Atualizar cliente    |
| DELETE | `/{id}` | Remover cliente      |

### Contas — `/api/accounts`

| Método | Rota               | Descrição            |
|--------|--------------------|----------------------|
| GET    | `/`                | Listar todas         |
| GET    | `/{id}`            | Buscar por ID        |
| GET    | `/{id}/balance`    | Consultar saldo      |
| GET    | `/client/{id}`     | Contas de um cliente |
| POST   | `/`                | Criar conta          |
| PUT    | `/{id}`            | Atualizar conta      |
| PATCH  | `/{id}/block`      | Bloquear conta       |
| PATCH  | `/{id}/close`      | Encerrar conta       |

### Transações — `/api/transactions`

| Método | Rota                | Descrição                 |
|--------|---------------------|---------------------------|
| POST   | `/transfer`         | Transferência entre contas|
| POST   | `/payment`          | Pagamento                 |
| POST   | `/pix`              | Operação PIX              |
| GET    | `/account/{id}`     | Transações de uma conta   |
| GET    | `/{id}`             | Buscar por ID             |

### Cartões — `/api/cards`

| Método | Rota              | Descrição            |
|--------|-------------------|----------------------|
| GET    | `/`               | Listar todos         |
| GET    | `/{id}`           | Buscar por ID        |
| GET    | `/account/{id}`   | Cartões de uma conta |
| POST   | `/`               | Criar cartão         |
| PUT    | `/{id}`           | Atualizar cartão     |
| PATCH  | `/{id}/block`     | Bloquear cartão      |
| DELETE | `/{id}`           | Remover cartão       |

### Chaves PIX — `/api/pix-keys`

| Método | Rota              | Descrição             |
|--------|-------------------|-----------------------|
| GET    | `/`               | Listar todas          |
| GET    | `/{id}`           | Buscar por ID         |
| GET    | `/account/{id}`   | Chaves de uma conta   |
| POST   | `/`               | Criar chave PIX       |
| DELETE | `/{id}`           | Remover chave PIX     |

### Produtos de Investimento — `/api/products`

| Método | Rota    | Descrição            |
|--------|---------|----------------------|
| GET    | `/`     | Listar todos         |
| GET    | `/{id}` | Buscar por ID        |
| POST   | `/`     | Criar produto        |
| PUT    | `/{id}` | Atualizar produto    |
| DELETE | `/{id}` | Remover produto      |

### Carteiras — `/api/wallets`

| Método | Rota            | Descrição               |
|--------|-----------------|-------------------------|
| GET    | `/`             | Listar todas            |
| GET    | `/{id}`         | Buscar por ID           |
| GET    | `/client/{id}`  | Carteiras de um cliente |
| POST   | `/`             | Criar carteira          |
| PUT    | `/{id}`         | Atualizar carteira      |
| DELETE | `/{id}`         | Remover carteira        |

### Ordens de Investimento — `/api/orders`

| Método | Rota            | Descrição            |
|--------|-----------------|----------------------|
| GET    | `/`             | Listar todas         |
| GET    | `/{id}`         | Buscar por ID        |
| GET    | `/account/{id}` | Ordens de uma conta  |
| POST   | `/`             | Criar ordem          |

### Relatórios — `/api/reports`

| Método | Rota                      | Descrição                        |
|--------|---------------------------|----------------------------------|
| GET    | `/clients`                | Relatório de clientes            |
| GET    | `/accounts`               | Relatório de contas              |
| GET    | `/cards`                  | Relatório de cartões             |
| GET    | `/pix-keys`               | Relatório de chaves PIX          |
| GET    | `/transactions`           | Relatório de transações          |
| GET    | `/products`               | Relatório de produtos            |
| GET    | `/orders`                 | Relatório de ordens              |
| GET    | `/transactions-by-period` | Transações por período           |
| GET    | `/investments`            | Relatório de investimentos       |

---

## Exemplos de Requisições

### Login

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"document":"12345678901","password":"123"}'
```

Resposta:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "clientId": 1,
  "legalName": "João Silva"
}
```

### Listar clientes (público)

```bash
curl http://localhost:8080/api/clients
```

### Criar conta (autenticado)

```bash
curl -X POST http://localhost:8080/api/accounts \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer SEU_TOKEN" \
  -d '{"clientId":1,"accountType":"CHECKING","branch":"0001"}'
```

### Transferência (autenticado)

```bash
curl -X POST http://localhost:8080/api/transactions/transfer \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer SEU_TOKEN" \
  -d '{"sourceAccountId":1,"destinationAccountId":4,"amount":100.00}'
```

### Consultar saldo (autenticado)

```bash
curl http://localhost:8080/api/accounts/1/balance \
  -H "Authorization: Bearer SEU_TOKEN"
```

### Relatório de transações por período (autenticado)

```bash
curl "http://localhost:8080/api/reports/transactions-by-period?start=2026-06-01T00:00:00&end=2026-07-01T00:00:00" \
  -H "Authorization: Bearer SEU_TOKEN"
```

---

## Credenciais de Teste

Os dados abaixo são inseridos pela migration `V2__seed_data.sql`:

| Documento     | Nome            | Tipo | Senha |
|---------------|-----------------|------|-------|
| 12345678901   | João Silva      | PF   | 123   |
| 11222333000181| Empresa ABC Ltda| PJ   | 123   |
| 98765432100   | Maria Souza     | PF   | 123   |

### Contas de teste

| Cliente      | Conta | Agência | Tipo     | Saldo   |
|--------------|-------|---------|----------|---------|
| João Silva   | 10001 | 0001    | CHECKING | R$ 5.000|
| João Silva   | 10002 | 0001    | SAVINGS  | R$ 15.000|
| Empresa ABC  | 20001 | 0002    | CHECKING | R$ 50.000|
| Maria Souza  | 30001 | 0001    | CHECKING | R$ 2.500|

---

## Swagger UI

A interface interativa do Swagger está disponível em:

```
http://localhost:8080/swagger-ui.html
```

Por lá é possível:
- Visualizar todos os endpoints organizados por controller
- Testar requisições diretamente pelo navegador
- Usar o botão **Authorize** para inserir o token JWT e testar endpoints autenticados

---

## Comandos Úteis (Maven)

```bash
mvn spring-boot:run      # Iniciar aplicação
mvn flyway:migrate       # Aplicar migrations pendentes
mvn flyway:info          # Status das migrations
mvn flyway:clean         # Limpar banco de dados
mvn clean compile        # Compilar o projeto
```

**Atenção:** `flyway:clean` apaga todos os dados do banco. Use apenas em desenvolvimento.
