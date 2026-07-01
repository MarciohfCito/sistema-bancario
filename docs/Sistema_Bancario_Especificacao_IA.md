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

1.  CLIENTES
2.  CONTAS
3.  CARTOES
4.  TRANSACOES
5.  CHAVES_PIX
6.  PRODUTOS_INVESTIMENTO
7.  CARTEIRAS_CLIENTES
8.  ORDEM_INVESTIMENTO

## Relacionamentos

CLIENTE 1:N CONTAS

CONTAS 1:N CARTOES

CONTAS 1:N CHAVES_PIX

CONTAS 1:N TRANSACOES (origem)

CONTAS 1:N TRANSACOES (destino)

CLIENTES 1:N CARTEIRAS_CLIENTES

PRODUTOS_INVESTIMENTO 1:N CARTEIRAS_CLIENTES

CONTAS 1:N ORDEM_INVESTIMENTO

PRODUTOS_INVESTIMENTO 1:N ORDEM_INVESTIMENTO

# Estrutura Esperada das Tabelas

## clientes

id_cliente PK tipo_cliente documento UNIQUE nome_razao email UNIQUE
telefone senha_hash data_cadastro

## contas

id_conta PK id_cliente FK numero_conta UNIQUE agencia tipo_conta saldo
limite_especial status

## cartoes

id_cartao PK id_conta FK numero_hash nome_impresso data_validade
cvv_hash tipo_cartao status

## transacoes

id_transacao PK id_conta_origem FK id_conta_destino FK valor
tipo_transacao data_hora status

## chaves_pix

id_chave PK id_conta FK tipo_chave valor_chave UNIQUE

## produtos_investimento

id_produto PK nome_produto tipo_ativo emissor indexador_taxa
data_vencimento status

## carteiras_clientes

id_carteira PK id_cliente FK id_produto FK quantidade valor_aplicado

## ordem_investimento

id_ordem PK id_conta FK id_produto FK tipo_ordem quantidade valor_total
data_solicitacao status

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
