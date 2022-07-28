CREATE TABLE Professor (
  CPF Integer,
  nome Varchar(100),
  dataNascimento Date,
  PRIMARY KEY (CPF)
);

CREATE TABLE CadastroAulas (
  codCadastroAulas Integer,
  CPFProfessor Integer,
  diaDaSemana Varchar(100),
  descAula Varchar(100),
  horarioAula Time,
  qtdMaximaAlunos Integer,
  PRIMARY KEY (codCadastroAulas),
  CONSTRAINT CPFProfessor FOREIGN KEY (CPFProfessor) REFERENCES Professor(CPF)
);

CREATE TABLE Aluno (
  matricula Integer,
  nome Varchar(100),
  dataNascimento Date,
  PRIMARY KEY (matricula)
);

CREATE TABLE CheckinsAluno (
  codCheckinsAluno Tipo,
  matriculaAluno Integer,
  codCadastroAulas Varchar(100),
  dataCheckin DateTime,
  qtdDisponivelAlunos Integer,
  PRIMARY KEY (codCheckinsAluno),
  CONSTRAINT matriculaAluno FOREIGN KEY (matriculaAluno) REFERENCES Aluno(matricula),
  CONSTRAINT codCadastroAulas FOREIGN KEY (codCadastroAulas) REFERENCES CadastroAulas(codCadastroAulas)
);

CREATE TABLE CheckinsRealizados (
  codCheckinsRealizados Integer,
  codCadastroAulas Varchar(100),
  qtdAtualAlunos Integer,
  qtdDisponivelAlunos Integer,
  PRIMARY KEY (codCheckinsRealizados),
  CONSTRAINT codCadastroAulas FOREIGN KEY (codCadastroAulas) REFERENCES CadastroAulas(codCadastroAulas)
);

