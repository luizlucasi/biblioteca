export interface IEmpresa {
  id?: number;
  nome?: string;
}

export class Empresa implements IEmpresa {
  constructor(public id?: number, public nome?: string) {}
}
