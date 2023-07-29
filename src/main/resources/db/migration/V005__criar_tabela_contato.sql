CREATE TABLE algamoneyapi.contato (
                                      codigo BIGINT auto_increment NOT NULL,
                                      nome varchar(100) NOT NULL,
                                      email varchar(100) NOT NULL,
                                      telefone varchar(15) NOT NULL,
                                      pessoa_codigo BIGINT NOT NULL,
                                      CONSTRAINT contato_pk PRIMARY KEY (codigo),
                                      CONSTRAINT contato_FK FOREIGN KEY (codigo) REFERENCES algamoneyapi.pessoas(codigo) ON DELETE RESTRICT ON UPDATE RESTRICT
)
    ENGINE=InnoDB
    DEFAULT CHARSET=utf8
    COMMENT='Tabela de contatos';
