# API_Version
Uma ApiRest para controle de versionamento de Programas.

*1 PASSO CRIAR TABELAS NO BANCO:*

create table tb_versao(
	id BIGINT auto_increment,
	id_prog BIGINT not NULL,
	file longblob,
	data_lancamento TIMESTAMP not null default CURRENT_TIMESTAMP,
	versao VARCHAR(255),
	user bigint,
	status tinyint,
	primary key(id)
)

create table tb_programa(
	id BIGINT auto_increment,
	nome varchar(255) not NULL,
	data_cadastro TIMESTAMP not null default CURRENT_TIMESTAMP,
	versao VARCHAR(255),
	user bigint,
	status tinyint,
	primary key(id)
)