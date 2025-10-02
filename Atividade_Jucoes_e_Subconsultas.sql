create database 20231164010004_lista_3;
use 20231164010004_lista_3;

create table Usuarios (
  id int auto_increment primary key,
  nome varchar(60) not null,
  cpf char(14) not null unique
);

create table Salas (
  id int auto_increment primary key,
  numero int not null unique,
  nome_completo varchar(100) not null unique,
  nome_curto varchar(20) not null unique
);

insert into Usuarios(nome, cpf) values
  ('Felipe', '111.111.111-11'),
  ('Marcelo', '222.222.222-22'),
  ('Mizael', '999.999.999-99'),
  ('Leonardo', '333.333.333-33');

insert into Salas(numero, nome_completo, nome_curto) values
  (21, 'Laboratório de Manutenção e Arquitetura de Computadores', 'LAMAC'),
  (46, 'Laboratório de Informática 1', 'LabInfo1'),
  (48, 'Laboratório de Informática 2', 'LabInfo2');

create table Permissao (
  expirar_acesso date,

  cpf_usuario char(14) not null,
  numero_sala int not null,
  
  primary key (cpf_usuario, numero_sala),

  foreign key (cpf_usuario) references Usuarios(cpf)
    on delete restrict on update cascade,
  foreign key (numero_sala) references Salas(numero)
    on delete restrict on update cascade
);

insert into Permissao(cpf_usuario, numero_sala, expirar_acesso) values
  ('111.111.111-11', 21, '2020-12-31'),
  ('111.111.111-11', 46, '2020-12-31'),
  ('111.111.111-11', 48, '2020-12-31'),
  ('222.222.222-22', 21, '2020-12-31'),
  ('222.222.222-22', 46, '2020-12-31'),
  ('222.222.222-22', 48, '2020-12-31'),
  ('333.333.333-33', 21, null);

-- Escreva uma consulta para mostrar o nome e o CPF de todos os usuários que têm permissão de acesso para a sala número 21.
select Usuarios.nome, Usuarios.cpf from Usuarios 
inner join Permissao on Usuarios.cpf = Permissao.cpf_usuario 
where Permissao.numero_sala = 21;


-- Escreva uma consulta para mostrar o número e o nome curto das salas que o usuário de CPF 111.111.111-11 tem permissão de acesso.
select Salas.numero, Salas.nome_curto from Salas
inner join Permissao on Salas.numero = Permissao.numero_sala
where Permissao.cpf_usuario = '111.111.111-11';


/* Escreva uma consulta que gera uma tabela de relatório indicando para cada usuário as salas que
ele tem acesso. Apresente o resultado ordenado pelo nome do usuário em primeiro lugar e pelo
número da sala em segundo lugar. As colunas da tabela serão nome, CPF, número, nome
completo, nome curto e a data de expirar acesso. */
select Usuarios.nome, Usuarios.cpf, Salas.numero, Salas.nome_completo, Salas.nome_curto, 
Permissao.expirar_acesso from Usuarios
inner join Permissao on Permissao.cpf_usuario = Usuarios.cpf
inner join Salas on Salas.numero = Permissao.numero_sala
order by Usuarios.nome ASC, Salas.numero ASC;
