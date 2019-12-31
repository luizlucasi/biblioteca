import { Moment } from 'moment';
import { ILivro } from 'app/shared/model/livro.model';
import { IAluno } from 'app/shared/model/aluno.model';

export interface IEmprestimo {
  id?: number;
  dataEmprestimo?: Moment;
  livro?: ILivro;
  aluno?: IAluno;
}

export class Emprestimo implements IEmprestimo {
  constructor(public id?: number, public dataEmprestimo?: Moment, public livro?: ILivro, public aluno?: IAluno) {}
}
