export interface ICircuito {
  id?: number;
  idControleDesignacao?: string;
  idEstacao?: string;
}

export class Circuito implements ICircuito {
  constructor(public id?: number, public idControleDesignacao?: string, public idEstacao?: string) {}
}
