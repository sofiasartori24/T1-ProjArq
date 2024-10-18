-- Criação da tabela Cliente
DROP TABLE IF EXISTS pagamentos; -- Excluir primeiro as tabelas dependentes
DROP TABLE IF EXISTS assinaturas;
DROP TABLE IF EXISTS aplicativos;
DROP TABLE IF EXISTS clientes;
DROP TABLE IF EXISTS usuarios;

CREATE TABLE clientes (
                          codigo BIGINT PRIMARY KEY,
                          nome VARCHAR(255) NOT NULL,
                          email VARCHAR(255) NOT NULL
);

-- Criação da tabela Aplicativo
CREATE TABLE aplicativos (
                             codigo BIGINT PRIMARY KEY,
                             nome VARCHAR(255) NOT NULL,
                             custo_mensal DOUBLE NOT NULL
);

-- Criação da tabela Assinatura
CREATE TABLE assinaturas (
                             codigo BIGINT PRIMARY KEY,
                             inicio_vigencia DATE NOT NULL,
                             fim_vigencia DATE,
                             aplicativo_codigo BIGINT,
                             FOREIGN KEY (aplicativo_codigo) REFERENCES aplicativos(codigo)
);

-- Criação da tabela Pagamento
CREATE TABLE pagamentos (
                            codigo BIGINT PRIMARY KEY,
                            valor_pago DOUBLE NOT NULL,
                            data_pagamento DATE NOT NULL,
                            promocao VARCHAR(255),
                            assinatura_codigo BIGINT,
                            FOREIGN KEY (assinatura_codigo) REFERENCES assinaturas(codigo)
);

-- Criação da tabela Usuario
CREATE TABLE usuarios (
                          usuario VARCHAR(255) PRIMARY KEY,
                          senha VARCHAR(255) NOT NULL
);
