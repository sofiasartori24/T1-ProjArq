-- Criação da tabela Cliente
DROP TABLE IF EXISTS pagamentos;
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
                             codigo BIGINT PRIMARY KEY AUTO_INCREMENT,
                             inicio_vigencia DATE NOT NULL,
                             fim_vigencia DATE,
                             aplicativo_codigo BIGINT,
                             cliente_codigo BIGINT,
                             FOREIGN KEY (aplicativo_codigo) REFERENCES aplicativos(codigo),
                             FOREIGN KEY (cliente_codigo) REFERENCES clientes(codigo)
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

-- Inserções de Clientes
INSERT INTO clientes (codigo, nome, email) VALUES
                                               (1, 'João Silva', 'joao.silva@example.com'),
                                               (2, 'Maria Oliveira', 'maria.oliveira@example.com'),
                                               (3, 'Pedro Santos', 'pedro.santos@example.com'),
                                               (4, 'Ana Costa', 'ana.costa@example.com'),
                                               (5, 'Lucas Almeida', 'lucas.almeida@example.com'),
                                               (6, 'Fernanda Lima', 'fernanda.lima@example.com'),
                                               (7, 'Bruno Pereira', 'bruno.pereira@example.com'),
                                               (8, 'Juliana Ribeiro', 'juliana.ribeiro@example.com'),
                                               (9, 'Ricardo Gomes', 'ricardo.gomes@example.com'),
                                               (10, 'Tatiane Martins', 'tatiane.martins@example.com');

-- Inserções de Aplicativos
INSERT INTO aplicativos (codigo, nome, custo_mensal) VALUES
                                                         (1, 'App A', 9.99),
                                                         (2, 'App B', 19.99),
                                                         (3, 'App C', 29.99),
                                                         (4, 'App D', 39.99),
                                                         (5, 'App E', 49.99);

-- Inserções de Assinaturas
INSERT INTO assinaturas (inicio_vigencia, fim_vigencia, aplicativo_codigo, cliente_codigo) VALUES
                                                                                                        ('2024-01-01', '2025-01-01', 1, 1),
                                                                                                       ('2024-02-01', '2025-02-01', 2, 2),
                                                                                                       ('2024-03-01', '2025-03-01', 3, 3),
                                                                                                       ('2024-04-01', '2025-04-01', 4, 4),
                                                                                                       ('2024-05-01', '2025-05-01', 5, 5);
