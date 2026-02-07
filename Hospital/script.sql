create database hospital;
use hospital;

create table paciente (
    id int primary key auto_increment,
    nome varchar(150) not null,
    cpf char(14) not null unique,
    doenca varchar(50) not null
);

create table medico (
    id int primary key auto_increment,
    nome varchar(150) not null,
    matricula int not null unique,
    especialidade varchar(50) not null,
    salario decimal(6,2) not null
);

create table consulta (
    id_medico int not null,
    id_paciente int not null,
    horario datetime not null,
    valor decimal(5,2),

    primary key (id_medico, id_paciente, horario),

    foreign key (id_medico) references medico(id)
        on update cascade
        on delete restrict,

    foreign key (id_paciente) references paciente(id)
        on update cascade
        on delete restrict
);

create index idx_nome_medico on medico(nome);
create index idx_nome_paciente on paciente(nome);

create unique index medico_horario on consulta (id_medico, horario);
create unique index paciente_horario on consulta (id_paciente, horario);
