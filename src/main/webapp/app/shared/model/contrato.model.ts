import { Moment } from 'moment';
import { INotaFiscal } from 'app/shared/model/nota-fiscal.model';
import { ICircuito } from 'app/shared/model/circuito.model';

export interface IContrato {
  id?: number;
  codigoClienteSap?: string;
  numeroContratoSap?: string;
  numeroContratoTpz?: string;
  nome?: string;
  status?: string;
  dataAssinatura?: Moment;
  dataReajuste?: Moment;
  dataTermino?: Moment;
  estado?: string;
  cnpj?: string;
  inscricaoEstadual?: string;
  vigencia?: number;
  notaFiscal?: INotaFiscal;
  circuito?: ICircuito;
}

export class Contrato implements IContrato {
  constructor(
    public id?: number,
    public codigoClienteSap?: string,
    public numeroContratoSap?: string,
    public numeroContratoTpz?: string,
    public nome?: string,
    public status?: string,
    public dataAssinatura?: Moment,
    public dataReajuste?: Moment,
    public dataTermino?: Moment,
    public estado?: string,
    public cnpj?: string,
    public inscricaoEstadual?: string,
    public vigencia?: number,
    public notaFiscal?: INotaFiscal,
    public circuito?: ICircuito
  ) {}
}
