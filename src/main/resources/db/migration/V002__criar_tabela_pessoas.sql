CREATE TABLE pessoas (
                                      codigo BIGINT auto_increment NOT NULL,
                                      nome varchar(200) NOT NULL,
                                      ativo BOOL NOT NULL,
                                      endereco_logradouro varchar(200) NULL,
                                      endereco_numero varchar(20) NULL,
                                      endereco_complemento varchar(200) NULL,
                                      endereco_bairro varchar(100) NULL,
                                      endereco_cidade varchar(100) NULL,
                                      endereco_estado VARCHAR(2) NULL,
                                      endereco_cep varchar(8) NULL,
                                      CONSTRAINT pessoas_pk PRIMARY KEY (codigo)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8
COMMENT='Cadastrar Pessoas';
