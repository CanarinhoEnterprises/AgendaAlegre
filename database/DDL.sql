CREATE TYPE status_espaco AS ENUM ('ATIVO','INTERDITADO','INATIVO');
CREATE TYPE status_reserva AS ENUM ('DOCUMENTOS_PENDENTES','EM_ANALISE','APROVADA','REJEITADA','AGUARDANDO_TERMO','CONFIRMADA','CANCELADA');
CREATE TYPE status_pendencia AS ENUM ('ABERTA','RESPONDIDA','CANCELADA');
CREATE TYPE tipo_pendencia AS ENUM ('DOCUMENTO','CORRECAO','INFORMACAO');
CREATE TYPE tipo_ocorrencia AS ENUM ('MANUTENCAO','DANO','LIMPEZA','SEGURANCA','OUTRO');
CREATE TYPE status_ocorrencia AS ENUM ('ABERTA','EM_ANDAMENTO','RESOLVIDA');
CREATE TYPE tipo_pessoa AS ENUM ('FISICA','JURIDICA');

CREATE TABLE Usuario(
 idUsuario INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
 nome VARCHAR(100) NOT NULL,
 telefone VARCHAR(20) NOT NULL,
 email VARCHAR(255) NOT NULL UNIQUE,
 senha VARCHAR(255) NOT NULL
);

CREATE TABLE Solicitante(
 idUsuario INT PRIMARY KEY,
 tipoPessoa tipo_pessoa NOT NULL,
 cpfCnpj VARCHAR(14) NOT NULL UNIQUE,
 CONSTRAINT fk_solicitante_usuario FOREIGN KEY(idUsuario) REFERENCES Usuario(idUsuario)
);

CREATE TABLE Administrador(
 idUsuario INT PRIMARY KEY,
 cpf CHAR(11) NOT NULL UNIQUE,
 CONSTRAINT fk_admin_usuario FOREIGN KEY(idUsuario) REFERENCES Usuario(idUsuario)
);

CREATE TABLE Cidade(
 idCidade INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
 UF CHAR(2) NOT NULL,
 nome VARCHAR(100) NOT NULL
);

CREATE TABLE Endereco(
 idEndereco INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
 idCidade INT NOT NULL,
 logradouro VARCHAR(100) NOT NULL,
 bairro VARCHAR(100) NOT NULL,
 cep CHAR(8) NOT NULL,
 num INT,
 comp VARCHAR(100),
 referencia VARCHAR(100),
 CONSTRAINT fk_endereco_cidade FOREIGN KEY(idCidade) REFERENCES Cidade(idCidade)
);

CREATE TABLE TipoEspaco(
 idTipoEspaco INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
 nome VARCHAR(100) NOT NULL,
 descricao TEXT
);

CREATE TABLE Espaco(
 idEspaco INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
 idEndereco INT NOT NULL,
 idTipoEspaco INT NOT NULL,
 capacidade INT NOT NULL CHECK(capacidade>0),
 nome VARCHAR(100) NOT NULL,
 descricao TEXT,
 urlCapa VARCHAR(500),
 status status_espaco NOT NULL,
 CONSTRAINT fk_espaco_endereco FOREIGN KEY(idEndereco) REFERENCES Endereco(idEndereco),
 CONSTRAINT fk_espaco_tipo FOREIGN KEY(idTipoEspaco) REFERENCES TipoEspaco(idTipoEspaco)
);

CREATE TABLE Equipamento(
 idEquipamento INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
 idEspaco INT NOT NULL,
 nome VARCHAR(100) NOT NULL,
 descricao TEXT NOT NULL,
 qtd INT NOT NULL CHECK(qtd>=0),
 CONSTRAINT fk_equipamento_espaco FOREIGN KEY(idEspaco) REFERENCES Espaco(idEspaco)
);

CREATE TABLE Reserva(
 idReserva INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
 idSolicitante INT NOT NULL,
 idEspaco INT NOT NULL,
 horaInicio TIME NOT NULL,
 dtUso DATE NOT NULL,
 horaFim TIME NOT NULL,
 finalidade VARCHAR(255) NOT NULL,
 dtSolicitacao TIMESTAMP NOT NULL,
 dtCancelamento TIMESTAMP, 
 dtConfirmacao TIMESTAMP,
 qtdPessoas INT NOT NULL CHECK(qtdPessoas>0),
 status status_reserva NOT NULL,
 CONSTRAINT chk_horario CHECK(horaFim>horaInicio),
 CONSTRAINT fk_reserva_sol FOREIGN KEY(idSolicitante) REFERENCES Solicitante(idUsuario),
 CONSTRAINT fk_reserva_espaco FOREIGN KEY(idEspaco) REFERENCES Espaco(idEspaco)
);

CREATE TABLE ModeloTermo(
 idModelo INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
 versao INT NOT NULL,
 titulo VARCHAR(100) NOT NULL,
 texto TEXT NOT NULL,
 descricao TEXT,
 dtCriacao DATE NOT NULL,
 ativo BOOLEAN NOT NULL
);

CREATE TABLE AnaliseReserva(
 numAnalise INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
 observacao TEXT,
 dtAnalise DATE NOT NULL,
 idReserva INT NOT NULL,
 idAdministrador INT NOT NULL,
 CONSTRAINT fk_analise_reserva FOREIGN KEY(idReserva) REFERENCES Reserva(idReserva),
 CONSTRAINT fk_analise_admin FOREIGN KEY(idAdministrador) REFERENCES Administrador(idUsuario)
);

CREATE TABLE TermoAceito(
 numTermo INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
 idReserva INT UNIQUE NOT NULL,
 dtGeracao TIMESTAMP NOT NULL,
 dtAceite TIMESTAMP,
 idModeloTermo INT,
 CONSTRAINT fk_termo_reserva FOREIGN KEY(idReserva) REFERENCES Reserva(idReserva),
 CONSTRAINT fk_termo_modelo FOREIGN KEY(idModeloTermo) REFERENCES ModeloTermo(idModelo)
);

CREATE TABLE Avaliacao(
 idAvaliacao INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
 idReserva INT NOT NULL,
 nota INT NOT NULL CHECK(nota BETWEEN 1 AND 5),
 comentario TEXT NOT NULL CHECK(length(comentario)<=1000),
 dtAvaliacao DATE NOT NULL,
 CONSTRAINT fk_avaliacao_reserva FOREIGN KEY(idReserva) REFERENCES Reserva(idReserva)
);

CREATE TABLE RecursoAcessibilidade(
 idRecurso INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
 descricao TEXT NOT NULL
);

CREATE TABLE Espaco_Recurso(
 idEspaco INT,
 idRecurso INT,
 CONSTRAINT pk_Espaco_Recurso PRIMARY KEY(idEspaco,idRecurso),
 CONSTRAINT fk_Espaco_Recurso_Espaco FOREIGN KEY(idEspaco) REFERENCES Espaco(idEspaco),
 CONSTRAINT fk_Espaco_Recurso_Recurso FOREIGN KEY(idRecurso) REFERENCES RecursoAcessibilidade(idRecurso)
);

CREATE TABLE Ocorrencia(
 idOcorrencia INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
 titulo VARCHAR(100) NOT NULL,
 descricao TEXT NOT NULL,
 dtRegistro DATE NOT NULL,
 tipo tipo_ocorrencia NOT NULL,
 idEspaco INT NOT NULL,
 status status_ocorrencia NOT NULL,
 CONSTRAINT fk_Ocorrencia_Espaco FOREIGN KEY(idEspaco) REFERENCES Espaco(idEspaco)
);

CREATE TABLE TipoDoc(
 idTipoDoc INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
 nome VARCHAR(100) NOT NULL,
 descricao TEXT
);

CREATE TABLE Pendencia(
 idPendencia INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
 dtCriacao TIMESTAMP NOT NULL,
 status status_pendencia NOT NULL,
 idReserva INT NOT NULL,
 idTipoDoc INT,
 dtResolucao TIMESTAMP,
 descricao TEXT NOT NULL,
 obsUsuario TEXT,
 idAdministrador INT,
 tipo tipo_pendencia NOT NULL,
 CONSTRAINT chk_datas CHECK(dtResolucao IS NULL OR dtResolucao>=dtCriacao),
 CONSTRAINT fk_Pendencia_Reserva FOREIGN KEY(idReserva) REFERENCES Reserva(idReserva),
 CONSTRAINT fk_Pendencia_TipoDoc FOREIGN KEY(idTipoDoc) REFERENCES TipoDoc(idTipoDoc),
 CONSTRAINT fk_Pendencia_Administrador FOREIGN KEY(idAdministrador) REFERENCES Administrador(idUsuario)
);

CREATE TABLE Arquivo(
 idArquivo INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
 idTipoDoc INT NOT NULL,
 idPendencia INT NOT NULL,
 nome VARCHAR(255) NOT NULL,
 url VARCHAR(500) NOT NULL,
 extensao VARCHAR(20) NOT NULL,
 tamanho BIGINT NOT NULL,
 dtUpload TIMESTAMP NOT NULL,
 CONSTRAINT fk_Arquivo_TipoDoc FOREIGN KEY(idTipoDoc) REFERENCES TipoDoc(idTipoDoc),
 CONSTRAINT fk_Arquivo_Pendencia FOREIGN KEY(idPendencia) REFERENCES Pendencia(idPendencia)
);

CREATE TABLE RestricaoReserva(
 idRestricao INT GENERATED ALWAYS AS IDENTITY,
 idTipoEspaco INT NOT NULL,
 finalidade VARCHAR(255),
 capacidadeMin INT,
 capacidadeMax INT,
 qtdPessoasMin INT,
 qtdPessoasMax INT,
 descricao TEXT NOT NULL,
 ativa BOOLEAN NOT NULL DEFAULT TRUE,
 idAdministrador INT NOT NULL,
 CONSTRAINT pk_restricao PRIMARY KEY(idRestricao),
 CONSTRAINT fk_restricao_tipoespaco FOREIGN KEY(idTipoEspaco) REFERENCES TipoEspaco(idTipoEspaco),
 CONSTRAINT fk_restricao_admin FOREIGN KEY(idAdministrador) REFERENCES Administrador(idUsuario),
 CONSTRAINT chk_capacidade CHECK(capacidadeMin IS NULL OR capacidadeMax IS NULL OR capacidadeMin<=capacidadeMax),
 CONSTRAINT chk_qtd CHECK(qtdPessoasMin IS NULL OR qtdPessoasMax IS NULL OR qtdPessoasMin<=qtdPessoasMax)
);

CREATE TABLE Restricao_TipoDoc(
 idRestricao INT NOT NULL,
 idTipoDoc INT NOT NULL,
 CONSTRAINT pk_restricao_tipodoc PRIMARY KEY(idRestricao,idTipoDoc),
 CONSTRAINT fk_rt_restricao FOREIGN KEY(idRestricao) REFERENCES RestricaoReserva(idRestricao) ON DELETE CASCADE,
 CONSTRAINT fk_rt_tipodoc FOREIGN KEY(idTipoDoc) REFERENCES TipoDoc(idTipoDoc)
);
