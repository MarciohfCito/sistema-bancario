# Sistema Bancário Digital - Especificação Completa para Agentes de IA

> Este documento consolida todos os requisitos do projeto em um formato
> otimizado para agentes de desenvolvimento (OpenCode, Claude Code,
> Cursor, etc.).

## Objetivo

Desenvolver um Sistema Bancário Digital utilizando MySQL, permitindo
gerenciamento de clientes, contas, cartões, PIX, transações e
investimentos.

## Escopo

-   Cadastro de clientes PF/PJ
-   Contas bancárias
-   Cartões
-   PIX
-   Transferências
-   Investimentos
-   Relatórios

# Requisitos Funcionais

## Clientes

-   Criar, editar, remover e consultar clientes.
-   Um cliente pode possuir várias contas.

## Contas

-   Criar, editar, bloquear, encerrar e consultar contas.
-   Consultar saldo em tempo real.

## Cartões

-   Criar, editar, bloquear e remover cartões.
-   Uma conta pode possuir vários cartões.

## PIX

-   Gerenciar múltiplas chaves PIX por conta.

## Transações

-   Transferências entre contas.
-   Pagamentos.
-   Operações PIX.
-   Registrar data/hora.
-   Registrar status.

## Investimentos

-   Cadastro de produtos.
-   Carteiras dos clientes.
-   Ordens de compra/venda.

## Relatórios

-   Clientes
-   Contas
-   Cartões
-   PIX
-   Transações
-   Produtos
-   Carteiras
-   Ordens
-   Histórico de transações

# Requisitos Não Funcionais

## Segurança

-   Senhas armazenadas em hash.
-   Criptografia de dados sensíveis.
-   Autenticação segura.
-   Logs financeiros.

## Confiabilidade

-   Backup automático.
-   Recuperação após falhas.
-   Integridade referencial.

## Eficiência

-   Consultas até 5 s.
-   Relatórios até 20 s.
-   Transações em tempo real.

## Portabilidade

-   MySQL.
-   Compatível com navegadores modernos.

# Modelo de Dados

## Entidades

1.  CLIENTS
2.  ACCOUNTS
3.  CARDS
4.  TRANSACTIONS
5.  PIX_KEYS
6.  INVESTMENT_PRODUCTS
7.  CLIENT_WALLETS
8.  INVESTMENT_ORDERS

## Relacionamentos

CLIENT 1:N ACCOUNTS

ACCOUNTS 1:N CARDS

ACCOUNTS 1:N PIX_KEYS

ACCOUNTS 1:N TRANSACTIONS (source)

ACCOUNTS 1:N TRANSACTIONS (destination)

CLIENTS 1:N CLIENT_WALLETS

INVESTMENT_PRODUCTS 1:N CLIENT_WALLETS

ACCOUNTS 1:N INVESTMENT_ORDERS

INVESTMENT_PRODUCTS 1:N INVESTMENT_ORDERS

# Estrutura Esperada das Tabelas

## clients

id PK client_type document UNIQUE legal_name email UNIQUE phone
password_hash created_at

## accounts

id PK client_id FK account_number UNIQUE branch account_type balance
overdraft_limit status

## cards

id PK account_id FK number_hash printed_name expiration_date
cvv_hash card_type status

## transactions

id PK source_account_id FK destination_account_id FK amount
transaction_type timestamp status

## pix_keys

id PK account_id FK key_type key_value UNIQUE

## investment_products

id PK product_name asset_type issuer rate_index
maturity_date status

## client_wallets

id PK client_id FK product_id FK quantity invested_amount

## investment_orders

id PK account_id FK product_id FK order_type quantity total_amount
request_date status

# Regras de Negócio

-   Não permitir contas sem cliente.
-   Não permitir cartões sem conta.
-   Não permitir chave PIX duplicada.
-   Documento e email únicos.
-   Número da conta único.
-   Transferências exigem contas válidas.
-   Ordens referenciam conta e produto existentes.

# Objetivo para o agente

Gerar uma API REST completa seguindo Clean Architecture, Spring Boot,
Spring Data JPA, MySQL, validações, DTOs, Services, Controllers,
Exceptions, documentação OpenAPI/Swagger e migrations.
