create database lista_04;
use lista_04;

create table alunos (
	numero int unique,
	nome varchar(50),
	curso varchar(30),
	nota1 double,
	nota2 double,
	nota3 double,
	nota4 double
);