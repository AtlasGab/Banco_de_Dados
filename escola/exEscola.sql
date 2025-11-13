CREATE DATABASE IF NOT EXISTS exEscola;
USE exEscola;

-- Tabelas e atributos --
CREATE TABLE IF NOT EXISTS Campus (
    codigo INT PRIMARY KEY,
    endereço VARCHAR(100) NOT NULL,
    cidade VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS Professor (
    matricula INT PRIMARY KEY,
    nome VARCHAR(80) NOT NULL,
    titulacao VARCHAR(50),
    codigo_campus INT NOT NULL,
    FOREIGN KEY (codigo_campus) REFERENCES Campus(codigo)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS Notebook (
    codigo INT PRIMARY KEY,
    data_compra DATE,
    codigo_campus INT NOT NULL,
    FOREIGN KEY (codigo_campus) REFERENCES Campus(codigo)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS Turma (
    codigo INT PRIMARY KEY,
    semestre INT,
    codigo_campus INT NOT NULL,
    FOREIGN KEY (codigo_campus) REFERENCES Campus(codigo)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS Aluno (
    matricula INT PRIMARY KEY,
    nome VARCHAR(80) NOT NULL,
    rendimento DECIMAL(3,1),
    codigo_turma INT NOT NULL,
    FOREIGN KEY (codigo_turma) REFERENCES Turma(codigo)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

-- Relacionamentos --
CREATE TABLE Professor_Leciona_Turma (
    matricula_professor INT,
    codigo_turma INT,
    PRIMARY KEY (matricula_professor, codigo_turma),
    FOREIGN KEY (matricula_professor) REFERENCES Professor(matricula)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
    
    FOREIGN KEY (codigo_turma) REFERENCES Turma(codigo)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

CREATE TABLE Professor_Adquire_Notebook (
    codigo_notebook INT PRIMARY KEY,
    matricula_professor INT UNIQUE,
    data_aquisicao DATE,
    FOREIGN KEY (matricula_professor) REFERENCES Professor(matricula)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
    
    FOREIGN KEY (codigo_notebook) REFERENCES Notebook(codigo)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);
