drop database if exists exIntroducaoJDBC;
create database exIntroducaoJDBC;

use exIntroducaoJDBC;

create table usuario (
    id int primary key not null auto_increment,
    nome varchar(50) not null,
    sexo enum('M', 'F') not null,
    idade int
);

