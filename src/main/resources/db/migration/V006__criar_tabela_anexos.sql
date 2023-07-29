CREATE TABLE anexos (
                     codigo BIGINT auto_increment NOT NULL,
                     nome_arquivo varchar(500) NOT NULL,
                     data_upload DATE NOT NULL,
                     codigo_lancamento BIGINT NOT NULL,
                     nome_arquivo_unico varchar(500) NOT NULL,
                     CONSTRAINT anexos_pk PRIMARY KEY (codigo),
                     CONSTRAINT anexos_FK FOREIGN KEY (codigo_lancamento) REFERENCES lancamentos(codigo) ON DELETE RESTRICT ON UPDATE RESTRICT
)
    ENGINE=InnoDB
    DEFAULT CHARSET=utf8
    COLLATE=utf8_general_ci
    COMMENT='Tabela que vai armazenar dados sobre os anexos';
