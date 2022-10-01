# API_Version
Uma <Strong>ApiRest</Strong> para controle de versionamento de Programas.
<ul>
<li>Tipo de Projeto: Maven</li><br/>
<li>Frameworks: SpringBoot WEB</li><br/>
<li>Tipo do Banco: MySQL</li><br/>
<li>Nome do Banco: api</li><br/>
<li>Usuário: api</li><br/>
<li>Senha: 123456789</li><br/>
</ul>
<h2>1 PASSO CRIAR TABELAS NO BANCO MYSQL:</h2>
<p>
CREATE TABLE tb_versao(<br/>
&nbsp;&nbsp;&nbsp;&nbsp;	id BIGINT auto_increment,<br/>
&nbsp;&nbsp;&nbsp;&nbsp;	id_prog BIGINT not NULL,<br/>
&nbsp;&nbsp;&nbsp;&nbsp;	file longblob,<br/>
&nbsp;&nbsp;&nbsp;&nbsp;	data_lancamento TIMESTAMP not null default CURRENT_TIMESTAMP,<br/>
&nbsp;&nbsp;&nbsp;&nbsp;	versao VARCHAR(255),<br/>
&nbsp;&nbsp;&nbsp;&nbsp;	user bigint,<br/>
&nbsp;&nbsp;&nbsp;&nbsp;	status tinyint,<br/>
&nbsp;&nbsp;&nbsp;&nbsp;	primary key(id)<br/>
);
</p><br/>
<hr/> 
<p>
CREATE TABLE tb_programa(<br/>
&nbsp;&nbsp;&nbsp;&nbsp;	id BIGINT auto_increment,<br/>
&nbsp;&nbsp;&nbsp;&nbsp;	nome varchar(255) not NULL,<br/>
&nbsp;&nbsp;&nbsp;&nbsp;	data_cadastro TIMESTAMP not null default CURRENT_TIMESTAMP,<br/>
&nbsp;&nbsp;&nbsp;&nbsp;	versao VARCHAR(255),<br/>
&nbsp;&nbsp;&nbsp;&nbsp;	user bigint,<br/>
&nbsp;&nbsp;&nbsp;&nbsp;	status tinyint,<br/>
&nbsp;&nbsp;&nbsp;&nbsp;	primary key(id)<br/>
);
</p>
