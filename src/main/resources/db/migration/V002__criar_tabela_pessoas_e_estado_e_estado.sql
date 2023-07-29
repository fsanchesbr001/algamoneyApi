CREATE TABLE estado (codigo BIGINT auto_increment NOT NULL,
                     sigla varchar(2) NOT NULL,
                     nome varchar(50) NOT NULL,
                     CONSTRAINT estado_pk PRIMARY KEY (codigo)
)
    ENGINE=InnoDB
    DEFAULT CHARSET=utf8
    COLLATE=utf8_general_ci
    COMMENT='Tabela com os Estados da Federacao';

INSERT INTO estado(codigo,sigla, nome) VALUES(1,'AC', 'Acre');
INSERT INTO estado(codigo,sigla, nome) VALUES(2,'AL', 'Alagoas');
INSERT INTO estado(codigo,sigla, nome) VALUES(3,'AP', 'Amapá');
INSERT INTO estado(codigo,sigla, nome) VALUES(4,'AM', 'Amazonas');
INSERT INTO estado(codigo,sigla, nome) VALUES(5,'BA', 'Bahia');
INSERT INTO estado(codigo,sigla, nome) VALUES(6,'CE', 'Ceará');
INSERT INTO estado(codigo,sigla, nome) VALUES(7,'DF', 'Distrito Federal');
INSERT INTO estado(codigo,sigla, nome) VALUES(8,'ES', 'Espírito Santo');
INSERT INTO estado(codigo,sigla, nome) VALUES(9,'GO', 'Goiás');
INSERT INTO estado(codigo,sigla, nome) VALUES(10,'MA', 'Maranhão');
INSERT INTO estado(codigo,sigla, nome) VALUES(11,'MT', 'Mato Grosso');
INSERT INTO estado(codigo,sigla, nome) VALUES(12,'MS', 'Mato Grosso do Sul');
INSERT INTO estado(codigo,sigla, nome) VALUES(13,'MG', 'Minas Gerais');
INSERT INTO estado(codigo,sigla, nome) VALUES(14,'PA', 'Pará');
INSERT INTO estado(codigo,sigla, nome) VALUES(15,'PB', 'Paraíba');
INSERT INTO estado(codigo,sigla, nome) VALUES(16,'PR', 'Paraná');
INSERT INTO estado(codigo,sigla, nome) VALUES(17,'PE', 'Pernambuco');
INSERT INTO estado(codigo,sigla, nome) VALUES(18,'PI', 'Piauí');
INSERT INTO estado(codigo,sigla, nome) VALUES(19,'RJ', 'Rio de Janeiro');
INSERT INTO estado(codigo,sigla, nome) VALUES(20,'RN', 'Rio Grande do Norte');
INSERT INTO estado(codigo,sigla, nome) VALUES(21,'RS', 'Rio Grande do Sul');
INSERT INTO estado(codigo,sigla, nome) VALUES(22,'RO', 'Rondônia');
INSERT INTO estado(codigo,sigla, nome) VALUES(23,'RR', 'Roraima');
INSERT INTO estado(codigo,sigla, nome) VALUES(24,'SC', 'Santa Catarina');
INSERT INTO estado(codigo,sigla, nome) VALUES(25,'SP', 'São Paulo');
INSERT INTO estado(codigo,sigla, nome) VALUES(26,'SE', 'Sergipe');
INSERT INTO estado(codigo,sigla, nome) VALUES(27,'TO', 'Tocantins');

CREATE TABLE cidade (codigo BIGINT auto_increment NOT NULL,
                     nome varchar(50) NOT NULL,
                     codigo_estado BIGINT NOT NULL,
                     CONSTRAINT cidade_pk PRIMARY KEY (codigo),
                     CONSTRAINT cidade_FK FOREIGN KEY (codigo_estado) REFERENCES estado(codigo)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_general_ci
COMMENT='Tabela de Cidades';

INSERT INTO cidade(codigo, nome,codigo_estado) VALUES(1,'Rio Branco', 1);
INSERT INTO cidade(codigo, nome,codigo_estado) VALUES(2,'Maceió',2);
INSERT INTO cidade(codigo, nome,codigo_estado) VALUES(3,'Macapá',3);
INSERT INTO cidade(codigo, nome,codigo_estado) VALUES(4,'Manaus',4);
INSERT INTO cidade(codigo, nome,codigo_estado) VALUES(5,'Salvador',5);
INSERT INTO cidade(codigo, nome,codigo_estado) VALUES(6,'Fortaleza',6);
INSERT INTO cidade(codigo, nome,codigo_estado) VALUES(7,'Brasilia',7);
INSERT INTO cidade(codigo, nome,codigo_estado) VALUES(8,'Vitória',8);
INSERT INTO cidade(codigo, nome,codigo_estado) VALUES(9,'Goiania',9);
INSERT INTO cidade(codigo, nome,codigo_estado) VALUES(10, 'São Luis',10);
INSERT INTO cidade(codigo, nome,codigo_estado) VALUES(11,'Cuiabá',11);
INSERT INTO cidade(codigo, nome,codigo_estado) VALUES(12,'Campo Grande',12);
INSERT INTO cidade(codigo, nome,codigo_estado) VALUES(13, 'Belo Horizonte',13);
INSERT INTO cidade(codigo, nome,codigo_estado) VALUES(14,'Belém',14);
INSERT INTO cidade(codigo, nome,codigo_estado) VALUES(15,'João Pessoa',15);
INSERT INTO cidade(codigo, nome,codigo_estado) VALUES(16,'Curitiba',16);
INSERT INTO cidade(codigo, nome,codigo_estado) VALUES(17,'Recife',17);
INSERT INTO cidade(codigo, nome,codigo_estado) VALUES(18,'Terezina',18);
INSERT INTO cidade(codigo, nome,codigo_estado) VALUES(19,'Rio de Janeiro',19);
INSERT INTO cidade(codigo, nome,codigo_estado) VALUES(20,'Natal',20);
INSERT INTO cidade(codigo, nome,codigo_estado) VALUES(21,'Porto Alegre',21);
INSERT INTO cidade(codigo, nome,codigo_estado) VALUES(22,'Porto Velho',22);
INSERT INTO cidade(codigo, nome,codigo_estado) VALUES(23,'Boa Vista',23);
INSERT INTO cidade(codigo, nome,codigo_estado) VALUES(24,'Florianópolis',24);
INSERT INTO cidade(codigo, nome,codigo_estado) VALUES(25,'São Paulo',25);
INSERT INTO cidade(codigo, nome,codigo_estado) VALUES(26,'Aracajú',26);
INSERT INTO cidade(codigo, nome,codigo_estado) VALUES(27,'Palmas',27);

CREATE TABLE pessoas (codigo BIGINT auto_increment NOT NULL,
                      nome varchar(200) NOT NULL,
                      ativo BOOL NOT NULL,
                      endereco_logradouro varchar(200) NULL,
                      endereco_numero varchar(20) NULL,
                      endereco_complemento varchar(200) NULL,
                      endereco_bairro varchar(100) NULL,
                      endereco_cep varchar(8) NULL,
                      codigo_cidade BIGINT,
                      CONSTRAINT pessoas_pk PRIMARY KEY (codigo),
                      CONSTRAINT pessoas_FK FOREIGN KEY (codigo_cidade) REFERENCES cidade(codigo)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8
COMMENT='Cadastrar Pessoas';

INSERT INTO pessoas (nome, ativo,endereco_logradouro,endereco_numero,endereco_complemento,
                     endereco_bairro, endereco_cep, codigo_cidade)
VALUES('Fabricio Sanches', 1, 'Avenida Nossa Senhora do Sabará', '4350', 'bloco 14 apto 13',
       'Pedreira','04447901',25);

INSERT INTO pessoas (nome, ativo,endereco_logradouro,endereco_numero,endereco_complemento,
                     endereco_bairro, endereco_cep,codigo_cidade)
VALUES('Luciana Sanches', 1, 'Avenida Professor Monjardino', '131', 'apto 54',
       'Vila Sonia', '05625160',25);

INSERT INTO pessoas (nome, ativo,endereco_logradouro,endereco_numero,endereco_complemento,
                     endereco_bairro,endereco_cep,codigo_cidade)
VALUES('Isabela Sanches', 1, 'Avenida Professor Monjardino', '131', 'apto 54',
       'Vila Sonia','05625160',25);
