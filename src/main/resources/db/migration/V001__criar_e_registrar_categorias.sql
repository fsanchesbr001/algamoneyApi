CREATE TABLE categorias (
    codigo BIGINT auto_increment NOT NULL,
    nome varchar(50) NOT NULL,
    CONSTRAINT categorias_pk PRIMARY KEY (codigo)
)
    ENGINE=InnoDB
    DEFAULT CHARSET=utf8
    COMMENT='Categorias de lançamentos';

INSERT INTO categorias(nome) values('Lazer');
INSERT INTO categorias(nome) values('Supermercado');
INSERT INTO categorias(nome) values('Alimentação');
INSERT INTO categorias(nome) values('Médicos');
INSERT INTO categorias(nome) values('Outros');
