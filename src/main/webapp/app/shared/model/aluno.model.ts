export interface IAluno {
  id?: number;
  nome?: string;
  turma?: number;
  matricula?: number;
  celular?: string;
}

export class Aluno implements IAluno {
  constructor(public id?: number, public nome?: string, public turma?: number, public matricula?: number, public celular?: string) {}
}
