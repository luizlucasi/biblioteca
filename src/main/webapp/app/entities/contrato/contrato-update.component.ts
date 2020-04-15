import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IContrato, Contrato } from 'app/shared/model/contrato.model';
import { ContratoService } from './contrato.service';
import { INotaFiscal } from 'app/shared/model/nota-fiscal.model';
import { NotaFiscalService } from 'app/entities/nota-fiscal/nota-fiscal.service';
import { ICircuito } from 'app/shared/model/circuito.model';
import { CircuitoService } from 'app/entities/circuito/circuito.service';

type SelectableEntity = INotaFiscal | ICircuito;

@Component({
  selector: 'jhi-contrato-update',
  templateUrl: './contrato-update.component.html'
})
export class ContratoUpdateComponent implements OnInit {
  isSaving = false;
  notafiscals: INotaFiscal[] = [];
  circuitos: ICircuito[] = [];
  dataAssinaturaDp: any;
  dataReajusteDp: any;
  dataTerminoDp: any;

  editForm = this.fb.group({
    id: [],
    codigoClienteSap: [],
    numeroContratoSap: [],
    numeroContratoTpz: [],
    nome: [],
    status: [],
    dataAssinatura: [],
    dataReajuste: [],
    dataTermino: [],
    estado: [],
    cnpj: [],
    inscricaoEstadual: [],
    vigencia: [],
    notaFiscal: [],
    circuito: []
  });

  constructor(
    protected contratoService: ContratoService,
    protected notaFiscalService: NotaFiscalService,
    protected circuitoService: CircuitoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contrato }) => {
      this.updateForm(contrato);

      this.notaFiscalService.query().subscribe((res: HttpResponse<INotaFiscal[]>) => (this.notafiscals = res.body || []));

      this.circuitoService.query().subscribe((res: HttpResponse<ICircuito[]>) => (this.circuitos = res.body || []));
    });
  }

  updateForm(contrato: IContrato): void {
    this.editForm.patchValue({
      id: contrato.id,
      codigoClienteSap: contrato.codigoClienteSap,
      numeroContratoSap: contrato.numeroContratoSap,
      numeroContratoTpz: contrato.numeroContratoTpz,
      nome: contrato.nome,
      status: contrato.status,
      dataAssinatura: contrato.dataAssinatura,
      dataReajuste: contrato.dataReajuste,
      dataTermino: contrato.dataTermino,
      estado: contrato.estado,
      cnpj: contrato.cnpj,
      inscricaoEstadual: contrato.inscricaoEstadual,
      vigencia: contrato.vigencia,
      notaFiscal: contrato.notaFiscal,
      circuito: contrato.circuito
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contrato = this.createFromForm();
    if (contrato.id !== undefined) {
      this.subscribeToSaveResponse(this.contratoService.update(contrato));
    } else {
      this.subscribeToSaveResponse(this.contratoService.create(contrato));
    }
  }

  private createFromForm(): IContrato {
    return {
      ...new Contrato(),
      id: this.editForm.get(['id'])!.value,
      codigoClienteSap: this.editForm.get(['codigoClienteSap'])!.value,
      numeroContratoSap: this.editForm.get(['numeroContratoSap'])!.value,
      numeroContratoTpz: this.editForm.get(['numeroContratoTpz'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      status: this.editForm.get(['status'])!.value,
      dataAssinatura: this.editForm.get(['dataAssinatura'])!.value,
      dataReajuste: this.editForm.get(['dataReajuste'])!.value,
      dataTermino: this.editForm.get(['dataTermino'])!.value,
      estado: this.editForm.get(['estado'])!.value,
      cnpj: this.editForm.get(['cnpj'])!.value,
      inscricaoEstadual: this.editForm.get(['inscricaoEstadual'])!.value,
      vigencia: this.editForm.get(['vigencia'])!.value,
      notaFiscal: this.editForm.get(['notaFiscal'])!.value,
      circuito: this.editForm.get(['circuito'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContrato>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
