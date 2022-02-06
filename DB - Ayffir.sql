create database ayffir;
use ayffir;

create table usuarios(
	ID int primary key auto_increment not null,
    Nome varchar(50) not null,
    Loja varchar(50),
    EMail varchar(150) not null unique,
    Senha varchar(20) not null,
    Cargo varchar(20) not null default "Administrador",
    IDUser int,
	foreign key (IDUser) references usuarios (ID)
);

insert into usuarios (`ID`, `Nome`, `Loja`, `EMail`, `Senha`, `Cargo`) VALUES (1, 'Vinícius da Cruz da Silva', 'Oglle', 'vinicius--cruz@hotmail.com', 'aezakmi4751', 'Administrador');

create table produtos(
	ID int primary key auto_increment not null,
    Codigo varchar(25) not null,
    Nome varchar(150) not null,
    Preco double not null,
    Vendido int(150) not null default 0,
    Prateleira int(150) not null default 0,
    Estoque int(150) not null default 0,
    IDUser int not null,
	foreign key (IDUser) references usuarios (ID)
);

create table reportes(
	ID int primary key auto_increment not null,
    Tipo varchar(150) not null,
    Urgencia varchar(150) not null,
    EMail varchar(150) not null,
    Titulo varchar(150) not null,
    Descricao varchar(500) not null,
    Status varchar (150) not null default 'Aguardando Resposta',
    IDUser int not null,
	foreign key (IDUser) references usuarios (ID)
);

create table atualizacao(
	ID int primary key auto_increment not null,
    AtualizacaoObrigatoria int not null default 0,
    Versao varchar (150) not null
);

insert into atualizacao (`ID`, `AtualizacaoObrigatoria`, `Versao`) VALUES (1, 0, '0.0.0.1');

select * from usuarios;