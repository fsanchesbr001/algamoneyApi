CREATE TABLE categorias (
    codigo BIGINT auto_increment NOT NULL,
    nome varchar(50) NOT NULL,
    CONSTRAINT categorias_pk PRIMARY KEY (codigo)
)
    ENGINE=InnoDB
    DEFAULT CHARSET=utf8
    COMMENT='Categorias de lançamentos';

INSERT INTO categorias(nome) values('Vestuário');
INSERT INTO categorias(nome) values('Transporte');
INSERT INTO categorias(nome) values('Alimentação');
INSERT INTO categorias(nome) values('Empréstimos');
INSERT INTO categorias(nome) values('Pensão Alimentícia');
INSERT INTO categorias(nome) values('Outros');
