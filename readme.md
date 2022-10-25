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

CREATE TABLE `tb_programa` (<br/>
&nbsp;&nbsp;&nbsp;&nbsp;  `id` bigint NOT NULL AUTO_INCREMENT,<br/>
&nbsp;&nbsp;&nbsp;&nbsp;  `nome` varchar(255) NOT NULL,<br/>
&nbsp;&nbsp;&nbsp;&nbsp;  `file_prog` longblob,<br/>
&nbsp;&nbsp;&nbsp;&nbsp;  `file_old` longblob,<br/>
&nbsp;&nbsp;&nbsp;&nbsp;  `data_cadastro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,<br/>
&nbsp;&nbsp;&nbsp;&nbsp;  `user` bigint DEFAULT NULL,<br/>
&nbsp;&nbsp;&nbsp;&nbsp;  `status` tinyint DEFAULT '0',<br/>
&nbsp;&nbsp;&nbsp;&nbsp;  PRIMARY KEY (`id`)<br/>
)

</p><br/>
<hr/> 
<p>

CREATE TABLE `tb_versao` (<br/>
&nbsp;&nbsp;&nbsp;&nbsp;  `id` bigint NOT NULL AUTO_INCREMENT,<br/>
&nbsp;&nbsp;&nbsp;&nbsp;  `id_prog` bigint NOT NULL,<br/>
&nbsp;&nbsp;&nbsp;&nbsp;  `data_lancamento` timestamp NULL DEFAULT CURRENT_TIMESTAMP,<br/>
&nbsp;&nbsp;&nbsp;&nbsp;  `versao` varchar(255) DEFAULT NULL,<br/>
&nbsp;&nbsp;&nbsp;&nbsp;  `user` bigint DEFAULT NULL,<br/>
&nbsp;&nbsp;&nbsp;&nbsp;  `status` tinyint DEFAULT NULL,<br/>
&nbsp;&nbsp;&nbsp;&nbsp;  PRIMARY KEY (`id`)<br/>
)
</p>
