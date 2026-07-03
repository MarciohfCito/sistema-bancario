# Relatório Técnico — Sistema Bancário Digital

**Instituição:** Universidade do Estado do Maranhão (UEMA)  
**Curso:** Engenharia da Computação  
**Disciplina:** Banco de Dados  
**Tecnologias:** Spring Boot 4.1.0 · MySQL 8.0 · Flyway · JWT · Swagger/OpenAPI  
**Repositório:** `br.uema.bd.banking_system`

---

## Resumo

Este relatório descreve o projeto e a implementação de um Sistema Bancário Digital baseado em uma API REST monolítica construída com Spring Boot 4.1.0 e MySQL 8.0. O sistema gerencia clientes (pessoas físicas e jurídicas), contas bancárias, cartões, chaves PIX, transações financeiras (transferências, pagamentos e PIX), produtos de investimento, carteiras de clientes e ordens de investimento. A autenticação é feita via JWT com senhas hasheadas com BCrypt. O versionamento do esquema de banco de dados é gerenciado pelo Flyway. A documentação interativa da API é gerada automaticamente pelo SpringDoc OpenAPI (Swagger).

---

## 1. Introdução

Sistemas bancários modernos exigem alta confiabilidade, rastreabilidade de operações e segurança no armazenamento de dados sensíveis. Este projeto acadêmico visa construir uma plataforma backend que atenda a esses requisitos, expondo funcionalidades bancárias por meio de uma API REST padronizada.

Os objetivos específicos são:

- Implementar operações CRUD para clientes, contas, cartões e chaves PIX.
- Realizar transações financeiras com suporte a transferências, pagamentos e PIX.
- Gerenciar produtos de investimento, carteiras e ordens de compra/venda.
- Gerar relatórios gerenciais (clientes, contas, transações por período, investimentos).
- Proteger os endpoints com autenticação JWT e senhas hasheadas.
- Versionar o banco de dados com Flyway para garantir integridade entre ambientes.

---

## 2. Arquitetura do Sistema

O sistema segue uma arquitetura em camadas (layered architecture) baseada nos princípios do Spring Boot:

```
┌─────────────────────────────────────────────────┐
│                   Controller                    │  (REST endpoints)
├─────────────────────────────────────────────────┤
│                     Service                      │  (Regras de negócio)
├─────────────────────────────────────────────────┤
│                     Repository                   │  (Acesso a dados / JPA)
├─────────────────────────────────────────────────┤
│                   Entity / JPA                   │  (Mapeamento objeto-relacional)
├─────────────────────────────────────────────────┤
│                 Banco de Dados                   │  (MySQL + Flyway)
└─────────────────────────────────────────────────┘
```

### Camadas

1. **Controller** — Expõe endpoints REST. Recebe requisições HTTP, valida dados de entrada com Jakarta Validation e delega para os serviços.
2. **Service** — Contém as regras de negócio. Coordena operações entre repositórios, aplica validações e transforma dados entre DTOs e entidades.
3. **Repository** — Interfaces Spring Data JPA que fornecem operações CRUD e consultas personalizadas.
4. **Entity** — Classes anotadas com `@Entity` que mapeiam as tabelas do banco de dados relacional.
5. **Security** — Filtro JWT, configuração de segurança com Spring Security e implementação de `UserDetailsService`.

### Pacotes do Projeto

```
br.uema.bd.banking_system
├── config          → OpenApiConfig (Swagger)
├── controller      → 10 controladores REST
├── dto             → 20 classes DTO (request/response)
├── entity          → 8 entidades JPA
├── exception       → 4 exceptions customizadas
├── repository      → 8 interfaces Spring Data JPA
├── security        → SecurityConfig, JwtAuthenticationFilter, UserDetailsServiceImpl
└── service         → 10 serviços + AuthService
```

---

## 3. Tecnologias Utilizadas

| Tecnologia                    | Versão      | Finalidade                              |
|-------------------------------|-------------|-----------------------------------------|
| Java                          | 21+         | Linguagem de programação                |
| Spring Boot                   | 4.1.0       | Framework de aplicação                  |
| Spring Data JPA               | (embutido)  | Mapeamento objeto-relacional            |
| Spring Security               | (embutido)  | Autenticação e autorização              |
| MySQL                         | 8.0.46      | Banco de dados relacional               |
| Flyway                        | (embutido)  | Versionamento de migrations             |
| JWT (jjwt)                    | 0.12.6      | Tokens de autenticação                  |
| BCrypt                        | (embutido)  | Hash de senhas                          |
| Lombok                        | (embutido)  | Redução de boilerplate (getters, etc.)  |
| SpringDoc OpenAPI             | 2.8.6       | Documentação Swagger UI                 |
| Maven                         | (wrapper)   | Gerenciamento de dependências e build   |

### Dependências Principais (pom.xml)

```xml
spring-boot-starter-data-jpa
spring-boot-starter-security
spring-boot-starter-validation
spring-boot-starter-webmvc
spring-boot-starter-oauth2-resource-server
mysql-connector-j (runtime)
flyway-core / flyway-mysql
jjwt-api / jjwt-impl / jjwt-jackson (0.12.6)
springdoc-openapi-starter-webmvc-ui (2.8.6)
lombok
```

---

## 4. Modelo de Dados

### 4.1 Entidades e Relacionamentos

O banco de dados é composto por 8 tabelas relacionadas:

```
clients (1) ──┐
               ├── (N) accounts ──┐
               │                   ├── (N) cards
               │                   ├── (N) pix_keys
               │                   ├── (N) transactions (source)
               │                   ├── (N) transactions (destination)
               │                   └── (N) investment_orders
               │
               └── (N) client_wallets ── (N) investment_products
```

### 4.2 Estrutura das Tabelas

#### clients
| Coluna         | Tipo         | Restrições              |
|----------------|--------------|-------------------------|
| id             | INT (PK)     | AUTO_INCREMENT          |
| client_type    | VARCHAR(20)  | NOT NULL                |
| document       | VARCHAR(20)  | NOT NULL, UNIQUE        |
| legal_name     | VARCHAR(150) | NOT NULL                |
| email          | VARCHAR(120) | NOT NULL, UNIQUE        |
| phone          | VARCHAR(20)  |                         |
| password_hash  | VARCHAR(255) | NOT NULL                |
| created_at     | DATETIME     |                         |

#### accounts
| Coluna          | Tipo          | Restrições             |
|-----------------|---------------|------------------------|
| id              | INT (PK)      | AUTO_INCREMENT         |
| client_id       | INT (FK)      | NOT NULL → clients     |
| account_number  | VARCHAR(20)   | UNIQUE                 |
| branch          | VARCHAR(10)   |                        |
| account_type    | VARCHAR(20)   |                        |
| balance         | DECIMAL(15,2) | DEFAULT 0              |
| overdraft_limit | DECIMAL(15,2) | DEFAULT 0              |
| status          | VARCHAR(20)   | DEFAULT 'active'       |

#### cards
| Coluna          | Tipo         | Restrições             |
|-----------------|--------------|------------------------|
| id              | INT (PK)     | AUTO_INCREMENT         |
| account_id      | INT (FK)     | NOT NULL → accounts    |
| number_hash     | VARCHAR(255) |                        |
| printed_name    | VARCHAR(100) |                        |
| expiration_date | DATE         |                        |
| cvv_hash        | VARCHAR(255) |                        |
| card_type       | VARCHAR(20)  |                        |
| status          | VARCHAR(20)  | DEFAULT 'active'       |

#### pix_keys
| Coluna     | Tipo         | Restrições                 |
|------------|--------------|----------------------------|
| id         | INT (PK)     | AUTO_INCREMENT             |
| account_id | INT (FK)     | NOT NULL → accounts        |
| key_type   | VARCHAR(20)  | NOT NULL (CPF/CNPJ/EMAIL/RANDOM/PHONE) |
| key_value  | VARCHAR(255) | NOT NULL, UNIQUE           |

#### transactions
| Coluna                | Tipo          | Restrições                    |
|-----------------------|---------------|-------------------------------|
| id                    | INT (PK)      | AUTO_INCREMENT                |
| source_account_id     | INT (FK)      | → accounts (pode ser NULL)    |
| destination_account_id| INT (FK)      | → accounts (pode ser NULL)    |
| amount                | DECIMAL(15,2) |                               |
| transaction_type      | VARCHAR(30)   |                               |
| timestamp             | DATETIME      |                               |
| status                | VARCHAR(20)   | DEFAULT 'pending'             |

#### investment_products
| Coluna        | Tipo         | Restrições        |
|---------------|--------------|-------------------|
| id            | INT (PK)     | AUTO_INCREMENT    |
| product_name  | VARCHAR(100) | NOT NULL          |
| asset_type    | VARCHAR(30)  | NOT NULL          |
| issuer        | VARCHAR(100) |                   |
| rate_index    | VARCHAR(50)  |                   |
| maturity_date | DATE         |                   |
| status        | VARCHAR(20)  | DEFAULT 'active'  |

#### client_wallets
| Coluna         | Tipo          | Restrições                |
|----------------|---------------|---------------------------|
| id             | INT (PK)      | AUTO_INCREMENT            |
| client_id      | INT (FK)      | → clients                 |
| product_id     | INT (FK)      | → investment_products     |
| quantity       | DECIMAL(15,2) | DEFAULT 0                 |
| invested_amount| DECIMAL(15,2) | DEFAULT 0                 |

#### investment_orders
| Coluna       | Tipo          | Restrições                |
|--------------|---------------|---------------------------|
| id           | INT (PK)      | AUTO_INCREMENT            |
| account_id   | INT (FK)      | → accounts                |
| product_id   | INT (FK)      | → investment_products     |
| order_type   | VARCHAR(10)   |                           |
| quantity     | DECIMAL(15,2) |                           |
| total_amount | DECIMAL(15,2) |                           |
| request_date | DATETIME      |                           |
| status       | VARCHAR(20)   | DEFAULT 'pending'         |

### 4.3 Restrições de Integridade

- `document` e `email` são únicos em `clients`.
- `account_number` é único em `accounts`.
- `key_value` é único em `pix_keys`.
- Chaves estrangeiras garantem integridade referencial em todos os relacionamentos.
- Chaves PIX não podem ser duplicadas (UNIQUE + regra de negócio).
- Transferências exigem contas de origem e destino válidas.

---

## 5. API REST

### 5.1 Autenticação — `/api/auth`

| Método | Rota       | Descrição                        | Autenticação |
|--------|------------|----------------------------------|--------------|
| POST   | `/login`   | Autentica e retorna token JWT    | Pública      |
| POST   | `/register`| Cria novo cliente e retorna token| Pública      |

### 5.2 Clientes — `/api/clients`

| Método | Rota      | Descrição               | Autenticação |
|--------|-----------|-------------------------|--------------|
| GET    | `/`       | Lista todos os clientes | Pública      |
| GET    | `/{id}`   | Busca cliente por ID    | Pública      |
| POST   | `/`       | Cria novo cliente       | JWT          |
| PUT    | `/{id}`   | Atualiza cliente        | JWT          |
| DELETE | `/{id}`   | Remove cliente          | JWT          |

### 5.3 Contas — `/api/accounts`

| Método | Rota               | Descrição               | Autenticação |
|--------|--------------------|-------------------------|--------------|
| GET    | `/`                | Lista todas as contas   | JWT          |
| GET    | `/{id}`            | Busca conta por ID      | JWT          |
| GET    | `/{id}/balance`    | Consulta saldo          | JWT          |
| GET    | `/client/{id}`     | Contas de um cliente    | JWT          |
| POST   | `/`                | Cria nova conta         | JWT          |
| PUT    | `/{id}`            | Atualiza conta          | JWT          |
| PATCH  | `/{id}/block`      | Bloqueia conta          | JWT          |
| PATCH  | `/{id}/close`      | Encerra conta           | JWT          |

### 5.4 Transações — `/api/transactions`

| Método | Rota                   | Descrição                  | Autenticação |
|--------|------------------------|----------------------------|--------------|
| POST   | `/transfer`            | Transferência entre contas | JWT          |
| POST   | `/payment`             | Pagamento                  | JWT          |
| POST   | `/pix`                 | Operação PIX               | JWT          |
| GET    | `/account/{id}`        | Transações de uma conta    | JWT          |
| GET    | `/{id}`                | Busca transação por ID     | JWT          |

### 5.5 Cartões — `/api/cards`

| Método | Rota            | Descrição           | Autenticação |
|--------|-----------------|---------------------|--------------|
| GET    | `/`             | Lista todos         | JWT          |
| GET    | `/{id}`         | Busca por ID        | JWT          |
| GET    | `/account/{id}` | Cartões de uma conta| JWT          |
| POST   | `/`             | Cria cartão         | JWT          |
| PUT    | `/{id}`         | Atualiza cartão     | JWT          |
| PATCH  | `/{id}/block`   | Bloqueia cartão     | JWT          |
| DELETE | `/{id}`         | Remove cartão       | JWT          |

### 5.6 Chaves PIX — `/api/pix-keys`

| Método | Rota            | Descrição             | Autenticação |
|--------|-----------------|-----------------------|--------------|
| GET    | `/`             | Lista todas           | JWT          |
| GET    | `/{id}`         | Busca por ID          | JWT          |
| GET    | `/account/{id}` | Chaves de uma conta   | JWT          |
| POST   | `/`             | Cria chave PIX        | JWT          |
| DELETE | `/{id}`         | Remove chave PIX      | JWT          |

### 5.7 Produtos de Investimento — `/api/products`

| Método | Rota    | Descrição            | Autenticação |
|--------|---------|----------------------|--------------|
| GET    | `/`     | Lista todos          | JWT          |
| GET    | `/{id}` | Busca por ID         | JWT          |
| POST   | `/`     | Cria produto         | JWT          |
| PUT    | `/{id}` | Atualiza produto     | JWT          |
| DELETE | `/{id}` | Remove produto       | JWT          |

### 5.8 Carteiras — `/api/wallets`

| Método | Rota      | Descrição             | Autenticação |
|--------|-----------|-----------------------|--------------|
| GET    | `/`       | Lista todas           | JWT          |
| GET    | `/{id}`   | Busca por ID          | JWT          |
| GET    | `/client/{id}` | Carteiras de um cliente | JWT      |
| POST   | `/`       | Cria carteira         | JWT          |
| PUT    | `/{id}`   | Atualiza carteira     | JWT          |
| DELETE | `/{id}`   | Remove carteira       | JWT          |

### 5.9 Ordens de Investimento — `/api/orders`

| Método | Rota      | Descrição             | Autenticação |
|--------|-----------|-----------------------|--------------|
| GET    | `/`       | Lista todas           | JWT          |
| GET    | `/{id}`   | Busca por ID          | JWT          |
| GET    | `/account/{id}` | Ordens de uma conta  | JWT      |
| POST   | `/`       | Cria ordem            | JWT          |

### 5.10 Relatórios — `/api/reports`

| Método | Rota                     | Descrição                       | Autenticação |
|--------|--------------------------|----------------------------------|--------------|
| GET    | `/clients`               | Relatório de clientes            | JWT          |
| GET    | `/accounts`              | Relatório de contas              | JWT          |
| GET    | `/cards`                 | Relatório de cartões             | JWT          |
| GET    | `/pix-keys`              | Relatório de chaves PIX          | JWT          |
| GET    | `/transactions`          | Relatório de transações          | JWT          |
| GET    | `/products`              | Relatório de produtos            | JWT          |
| GET    | `/orders`                | Relatório de ordens              | JWT          |
| GET    | `/transactions-by-period`| Transações por período (start/end)| JWT         |
| GET    | `/investments`           | Relatório consolidado de investimentos | JWT     |

---

## 6. Segurança

### 6.1 Autenticação JWT

O sistema utiliza tokens JWT (JSON Web Token) para autenticação stateless:

1. O cliente envia suas credenciais (documento + senha) para `POST /api/auth/login`.
2. O servidor valida as credenciais contra o hash BCrypt armazenado.
3. Em caso de sucesso, um token JWT é gerado com expiração configurável (padrão: 24 horas).
4. O token deve ser enviado no header `Authorization: Bearer <token>` em requisições autenticadas.

### 6.2 Hash de Senhas

As senhas são armazenadas exclusivamente como hashes BCrypt. O algoritmo BCrypt incorpora um salt aleatório, protegendo contra ataques de rainbow table.

### 6.3 Configuração de Segurança

```java
http
  .csrf(csrf -> csrf.disable())
  .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
  .authorizeHttpRequests(auth -> auth
    .requestMatchers(POST, "/api/auth/**").permitAll()
    .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
    .requestMatchers(GET, "/api/clients/**").permitAll()
    .anyRequest().authenticated()
  )
  .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
```

- Endpoints de autenticação (`/api/auth/**`) são públicos.
- Swagger UI e OpenAPI docs são públicos.
- Consulta de clientes (`GET /api/clients/**`) é pública.
- Todos os demais endpoints exigem token JWT válido.

---

## 7. Padrões de Projeto

### 7.1 DTO (Data Transfer Object)

Todas as requisições e respostas da API utilizam DTOs para desacoplar a representação externa dos dados do modelo interno (entidades JPA). São 20 classes DTO divididas entre `*Request.java` e `*Response.java`.

### 7.2 Repository Pattern

As interfaces `*Repository` estendem `JpaRepository` do Spring Data, abstraindo o acesso ao banco de dados e fornecendo métodos CRUD prontos, além de consultas personalizadas via `@Query` ou derived queries.

### 7.3 Service Layer

Cada entidade possui um service correspondente que encapsula as regras de negócio. Os services orquestram chamadas a repositórios, aplicam validações e convertem entre entidades e DTOs.

### 7.4 Exception Handler

O projeto utiliza exceptions customizadas (`ResourceNotFoundException`, `BusinessRuleException`, etc.) que são tratadas globalmente para retornar respostas HTTP padronizadas.

### 7.5 Filter Chain (JWT)

Um filtro `JwtAuthenticationFilter` é registrado na cadeia do Spring Security para interceptar requisições, extrair e validar o token JWT, e configurar o contexto de autenticação.

---

## 8. Migrações com Flyway

O Flyway gerencia o versionamento do esquema do banco de dados:

- **V1__initial_schema.sql**: Criação de todas as 8 tabelas com constraints, chaves primárias e estrangeiras.
- **V2__seed_data.sql**: Dados iniciais para testes (3 clientes, 4 contas, 3 cartões, 6 chaves PIX, 5 transações, 4 produtos, 4 carteiras, 3 ordens).

Comandos úteis:

```bash
mvn flyway:migrate    # Aplica migrations pendentes
mvn flyway:clean      # Limpa o banco (cuidado: destrutivo)
mvn flyway:info       # Status das migrations
```

O `ddl-auto` está configurado como `validate`, ou seja, o Hibernate valida se as entidades JPA são compatíveis com o schema existente, sem tentar criar/alterar tabelas.

---

## 9. Configuração do Ambiente

### application.properties

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/banking_system?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=vh
spring.datasource.password=12345

spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true

spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration

api.security.token.secret=${JWT_SECRET:minha-chave-super-secreta-sistema-bancario-2024}
api.security.token.expiration=${JWT_EXPIRATION:86400000}

server.port=8080
```

### Credenciais de Teste (Seed Data)

| Documento     | Nome            | Senha |
|---------------|-----------------|-------|
| 12345678901   | João Silva      | 123   |
| 11222333000181| Empresa ABC Ltda| 123   |
| 98765432100   | Maria Souza     | 123   |

---

## 10. Documentação da API

A documentação interativa (Swagger UI) está disponível em:

```
http://localhost:8080/swagger-ui.html
```

O JSON/ YAML da especificação OpenAPI está em:

```
http://localhost:8080/api-docs
```

O Swagger UI permite testar todos os endpoints diretamente pelo navegador, incluindo a funcionalidade de "Authorize" para inserir o token JWT.

---

## 11. Como Executar

### Pré-requisitos

- Java 21+ (JDK)
- MySQL 8.0+
- Maven (ou usar o Maven Wrapper `./mvnw`)

### Passos

1. Criar o banco de dados:
   ```sql
   CREATE DATABASE banking_system;
   ```

2. Configurar credenciais em `application.properties` (usuário `vh`, senha `12345`).

3. Aplicar migrations:
   ```bash
   mvn flyway:migrate
   ```

4. Iniciar a aplicação:
   ```bash
   mvn spring-boot:run
   ```

5. Acessar: `http://localhost:8080/swagger-ui.html`

---

## 12. Considerações Finais

O Sistema Bancário Digital implementa todas as funcionalidades propostas: gerenciamento de clientes, contas, cartões, PIX, transações e investimentos. A arquitetura em camadas garante separação de responsabilidades e facilita manutenção futura. A autenticação JWT com BCrypt protege os dados sensíveis, e o Flyway assegura que o esquema do banco de dados permaneça versionado e consistente entre diferentes ambientes (desenvolvimento, homologação, produção).

Trabalhos futuros podem incluir: testes unitários e de integração abrangentes, cache com Redis para consultas frequentes, filas de mensagens (RabbitMQ/Kafka) para processamento assíncrono de transações, e uma interface frontend (React/Angular/Vue) para consumo da API.

---

## Referências

- Spring Boot Documentation. Disponível em: https://docs.spring.io/spring-boot/index.html
- Flyway Documentation. Disponível em: https://documentation.red-gate.com/flyway
- JSON Web Tokens (JWT). Disponível em: https://jwt.io/introduction
- BCrypt. Disponível em: https://en.wikipedia.org/wiki/Bcrypt
- SpringDoc OpenAPI. Disponível em: https://springdoc.org
- OpenAPI Specification. Disponível em: https://swagger.io/specification/
