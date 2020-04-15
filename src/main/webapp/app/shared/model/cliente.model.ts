import { IContrato } from 'app/shared/model/contrato.model';

export interface ICliente {
  id?: number;
  codCliente?: string;
  contrato?: IContrato;
}

export class Cliente implements ICliente {
  constructor(public id?: number, public codCliente?: string, public contrato?: IContrato) {}
}
