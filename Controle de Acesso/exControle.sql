create database Controle_de_Acesso;
use Controle_de_Acesso;

-- Tabelas e Atributos --
create table Tag (
    id int primary key auto_increment,
    codigo int unique not null,
    tipo varchar(50)
);

create table Sala (
    id int primary key auto_increment,
    numero int unique not null,
    nome varchar(50)
);

create table Usuario (
    id int primary key auto_increment,
    nome varchar(50) not null,
    endereco varchar(100)
);

create table Servidor (
    id_usuario int primary key,
    matricula varchar(50) unique not null,
    
    foreign key(id_usuario) references Usuario(id)
        on delete restrict
        on update cascade
);

create table Externo (
    id_usuario int primary key,
    matricula_supervisor varchar(50) not null,
    cpf varchar(11) unique not null,
    
    foreign key(id_usuario) references Usuario(id)
        on delete restrict
        on update cascade,
        
    foreign key(matricula_supervisor) references Servidor(matricula)
        on delete restrict
        on update cascade
);

-- Relacionamentos --
create table Permissao_Sala (
    id_sala int,
    id_usuario int,
    horario datetime not null,
    primary key(id_sala, id_usuario, horario),
    
    foreign key(id_sala) references Sala(id)
        on delete restrict
        on update cascade,
        
    foreign key(id_usuario) references Usuario(id)
        on delete restrict
        on update cascade
);

create table Usuario_Possui_Tag (
    id_tag int,
    id_usuario int,
    horario datetime not null,
    primary key(id_tag),
    
    foreign key(id_tag) references Tag(id)
        on delete restrict
        on update cascade,
        
    foreign key(id_usuario) references Usuario(id)
        on delete restrict
        on update cascade
);

create table Usuario_Tenta_Acessar (
    id_tag int,
    id_sala int,
    id_usuario int,
    horario datetime not null,
    permitido boolean,
    primary key(id_usuario, id_tag, id_sala, horario),
    
    foreign key(id_tag) references Tag(id)
        on delete restrict
        on update cascade,
        
    foreign key(id_sala) references Sala(id)
        on delete restrict
        on update cascade,
        
    foreign key(id_usuario) references Usuario(id)
        on delete restrict
        on update cascade
);

