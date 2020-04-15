import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICliente, Cliente } from 'app/shared/model/cliente.model';
import { ClienteService } from './cliente.service';
import { IContrato } from 'app/shared/model/contrato.model';
import { ContratoService } from 'app/entities/contrato/contrato.service';

@Component({
  selector: 'jhi-cliente-update',
  templateUrl: './cliente-update.component.html'
})
export class ClienteUpdateComponent implements OnInit {
  isSaving = false;
  contratoes: IContrato[] = [];

  editForm = this.fb.group({
    id: [],
    codCliente: [],
    contrato: []
  });

  constructor(
    protected clienteService: ClienteService,
    protected contratoService: ContratoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cliente }) => {
      this.updateForm(cliente);

      this.contratoService.query().subscribe((res: HttpResponse<IContrato[]>) => (this.contratoes = res.body || []));
    });
  }

  updateForm(cliente: ICliente): void {
    this.editForm.patchValue({
      id: cliente.id,
      codCliente: cliente.codCliente,
      contrato: cliente.contrato
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cliente = this.createFromForm();
    if (cliente.id !== undefined) {
      this.subscribeToSaveResponse(this.clienteService.update(cliente));
    } else {
      this.subscribeToSaveResponse(this.clienteService.create(cliente));
    }
  }

  private createFromForm(): ICliente {
    return {
      ...new Cliente(),
      id: this.editForm.get(['id'])!.value,
      codCliente: this.editForm.get(['codCliente'])!.value,
      contrato: this.editForm.get(['contrato'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICliente>>): void {
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

  trackById(index: number, item: IContrato): any {
    return item.id;
  }
}
