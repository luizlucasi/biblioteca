import { IAutor } from 'app/shared/model/autor.model';

export interface ILivro {
  id?: number;
  isbn?: string;
  nome?: string;
  editora?: string;
  capaContentType?: string;
  capa?: any;
  tombo?: number;
  autors?: IAutor[];
}

export class Livro implements ILivro {
  constructor(
    public id?: number,
    public isbn?: string,
    public nome?: string,
    public editora?: string,
    public capaContentType?: string,
    public capa?: any,
    public tombo?: number,
    public autors?: IAutor[]
  ) {}
}
