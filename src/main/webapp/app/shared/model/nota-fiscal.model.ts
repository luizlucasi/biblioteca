export interface INotaFiscal {
  id?: number;
  numero?: string;
  competencia?: string;
  tipo?: string;
  servico?: string;
  condicaoPagamento?: string;
  totalNF?: number;
}

export class NotaFiscal implements INotaFiscal {
  constructor(
    public id?: number,
    public numero?: string,
    public competencia?: string,
    public tipo?: string,
    public servico?: string,
    public condicaoPagamento?: string,
    public totalNF?: number
  ) {}
}
