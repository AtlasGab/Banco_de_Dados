create database Empresa_de_Desenvolvimento;

use Empresa_de_Desenvolvimento;

create table Setor (
    codigo int primary key,
    nome varchar(50) not null
);

create table Empregado (
    cpf varchar(11) primary key,
    nome varchar(80) not null,
    
    codigo_setor int not null, -- Relação lotado
    foreign key(codigo_setor) references Setor(codigo)
        on delete restrict
        on update cascade
);

create table Engenheiro (
    cpf varchar(11) primary key,
    numero_de_registro int unique not null,
    
    foreign key(cpf) references Empregado(cpf)
        on delete restrict
        on update cascade

);

create table Programador (
    cpf varchar(11) primary key,
    especialidade varchar(70) not null,
    
    foreign key(cpf) references Empregado(cpf)
        on delete restrict
        on update cascade
);

create table Projeto (
    codigo int primary key,
    nome varchar(100) not null,

    coordenador_engenheiro int unique not null, --relação de coordenador
    data_coordenacao date,

    foreign key(coordenador_engenheiro) references Engenheiro(numero_de_registro) 
        on delete restrict
        on update cascade
);



create table Programador_Participa_Projeto (
    cpf_programador varchar(11),
    codigo_projeto int,
    primary key(cpf_programador, codigo_projeto),
    
    data_participacao date,
    
    foreign key(codigo_projeto) references Projeto(codigo)
        on delete restrict
        on update cascade,
    
    foreign key(cpf_programador) references Programador(cpf)
        on delete restrict
        on update cascade
);

create table Linguagem_de_Programacao (
    nome varchar(20) primary key,
    multiplataforma boolean,
    aberta boolean
    
);

create table Programador_Domina_Linguagem (
    cpf_programador varchar(11),
    linguagem varchar(20),
    primary key(cpf_programador, linguagem),
    
    foreign key(cpf_programador) references Programador(cpf)
        on delete restrict
        on update cascade,
    
    foreign key(linguagem) references Linguagem_de_Programacao(nome)
        on delete restrict
        on update cascade
); -- não tem como colocar que o programador saiba no mínimo uma linguagem com o conhecimento atual da disciplina, pesquisar futuramente.
