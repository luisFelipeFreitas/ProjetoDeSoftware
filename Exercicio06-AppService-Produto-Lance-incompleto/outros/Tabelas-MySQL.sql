https://dev.mysql.com/downloads/

Download MySQL Community Server

Últimas versões:
- 5.5
- 5.6
- 5.7
- 8.0.12 (atual)

DROP TABLE banco.lance;
DROP TABLE banco.produto;
DROP TABLE banco.habilidade;
DROP TABLE banco.equipamento;
DROP TABLE banco.personagem;
DROP TABLE banco.jogador;
DROP TABLE banco.usuarios;
DROP TABLE banco.perfis;

CREATE TABLE banco.produto (
  id              INT(11) NOT NULL AUTO_INCREMENT,
  nome            VARCHAR(30) NOT NULL,
  descricao       VARCHAR(50) DEFAULT '',
  lance_minimo    DECIMAL(8, 2) NOT NULL,
  data_cadastro   DATE NOT NULL,
  data_venda      DATE DEFAULT NULL,
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci;

CREATE TABLE banco.lance (
  id                INT(11) NOT NULL AUTO_INCREMENT,
  valor             DECIMAL(10, 2) NOT NULL,
  data_criacao      DATE NOT NULL,
  produto_id        INT(11) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT PRODUTO_LANCE_FK 
  FOREIGN KEY (produto_id)
  REFERENCES banco.produto(id) 
  ON DELETE NO ACTION ON UPDATE RESTRICT
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci;

INSERT INTO produto(NOME, DESCRICAO, LANCE_MINIMO, DATA_CADASTRO)
VALUES('TV SAMSUNG 20 POL', 'TV SAMSUNG 20 POL TELA PLANA', 2000, now());

INSERT INTO LANCE(VALOR, DATA_CRIACAO, PRODUTO_ID) VALUES
(2100, now(), LAST_INSERT_ID()),
(2200, now(), LAST_INSERT_ID());

INSERT INTO produto(NOME, DESCRICAO, LANCE_MINIMO, DATA_CADASTRO)
VALUES('TV SAMSUNG 22 POL', 'TV SAMSUNG 22 POL TELA PLANA', 2500, now());

INSERT INTO LANCE(VALOR, DATA_CRIACAO, PRODUTO_ID) VALUES
(2600, now(), LAST_INSERT_ID()),
(2700, now(), LAST_INSERT_ID());

CREATE TABLE banco.jogador (
  id               INT(11) NOT NULL AUTO_INCREMENT,
  username         VARCHAR(30) NOT NULL,
  senha		       VARCHAR(50) NOT NULL,
  email		       VARCHAR(50) NOT NULL,  
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci;


CREATE TABLE banco.personagem (
  id                INT(11) NOT NULL AUTO_INCREMENT,
  nome             VARCHAR(30) NOT NULL,
  sexo      		VARCHAR(30) NOT NULL,
  classe      		VARCHAR(30) NOT NULL,
  jogador_id        	INT(11) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT JOGADOR_PERSONAGEM_FK 
  FOREIGN KEY (jogador_id)
  REFERENCES banco.jogador(id) 
  ON DELETE NO ACTION ON UPDATE RESTRICT
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci;

CREATE TABLE banco.equipamento (
  id                INT(11) NOT NULL AUTO_INCREMENT,
  nome             VARCHAR(30) NOT NULL,
  tipo      		VARCHAR(30) NOT NULL,
  elemento     		VARCHAR(30) NOT NULL,
  personagem_id        	INT(11) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT PERSONAGEM_EQUIPAMENTO_FK 
  FOREIGN KEY (personagem_id)
  REFERENCES banco.personagem(id) 
  ON DELETE NO ACTION ON UPDATE RESTRICT
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci;

CREATE TABLE banco.habilidade (
  id                INT(11) NOT NULL AUTO_INCREMENT,
  nome              VARCHAR(30) NOT NULL,
  efeito      		VARCHAR(30) NOT NULL,
  cooldown     		double NOT NULL,
  equipamento_id     INT(11) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT EQUIPAMENTO_HABILIDADE_FK 
  FOREIGN KEY (equipamento_id)
  REFERENCES banco.equipamento(id) 
  ON DELETE NO ACTION ON UPDATE RESTRICT
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci;


INSERT INTO jogador(username,senha,email) VALUES
("luis","UFF@123","luisfreitas@id.uff.br");

INSERT INTO personagem(nome, sexo, classe, jogador_id) VALUES
("Dragonborn","masculino","Fighter",1);


CREATE TABLE banco.usuarios (
  conta         VARCHAR(30) NOT NULL,
  senha		       VARCHAR(50) NOT NULL,  
  PRIMARY KEY (conta)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci;

CREATE TABLE banco.perfis (
  id                INT(11) NOT NULL AUTO_INCREMENT,
  conta 			VARCHAR(30) NOT NULL,
  perfil            VARCHAR(30) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT USUARIO_PERFIL_FK 
  FOREIGN KEY (conta)
  REFERENCES banco.usuarios(conta) 
  ON DELETE NO ACTION ON UPDATE RESTRICT
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci;