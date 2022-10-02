CREATE TABLE lancamentos (
    codigo BIGINT(20) auto_increment NOT NULL,
    descricao varchar(50) NOT NULL,
    data_vencimento DATE NOT NULL,
    data_pagamento DATE NULL,
    valor DECIMAL(10,2) NOT NULL,
    observacao varchar(100) NULL,
    tipo varchar(20) NOT NULL,
    codigo_categoria BIGINT(20) NOT NULL,
    codigo_pessoa BIGINT(20) NOT NULL,
    CONSTRAINT lancamentos_pk PRIMARY KEY (codigo),
    CONSTRAINT lancamentos_categorias_FK FOREIGN KEY (codigo_categoria) REFERENCES categorias(codigo),
    CONSTRAINT lancamentos_pessoas_FK FOREIGN KEY (codigo_pessoa) REFERENCES pessoas(codigo)
)
    ENGINE=InnoDB
    DEFAULT CHARSET=utf8mb4
    COLLATE=utf8mb4_0900_ai_ci
    COMMENT='Tabela para cadastrar os lançamentos do sistema';
