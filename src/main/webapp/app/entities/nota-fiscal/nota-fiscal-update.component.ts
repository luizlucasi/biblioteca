import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { INotaFiscal, NotaFiscal } from 'app/shared/model/nota-fiscal.model';
import { NotaFiscalService } from './nota-fiscal.service';

@Component({
  selector: 'jhi-nota-fiscal-update',
  templateUrl: './nota-fiscal-update.component.html'
})
export class NotaFiscalUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    numero: [],
    competencia: [],
    tipo: [],
    servico: [],
    condicaoPagamento: [],
    totalNF: []
  });

  constructor(protected notaFiscalService: NotaFiscalService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ notaFiscal }) => {
      this.updateForm(notaFiscal);
    });
  }

  updateForm(notaFiscal: INotaFiscal): void {
    this.editForm.patchValue({
      id: notaFiscal.id,
      numero: notaFiscal.numero,
      competencia: notaFiscal.competencia,
      tipo: notaFiscal.tipo,
      servico: notaFiscal.servico,
      condicaoPagamento: notaFiscal.condicaoPagamento,
      totalNF: notaFiscal.totalNF
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const notaFiscal = this.createFromForm();
    if (notaFiscal.id !== undefined) {
      this.subscribeToSaveResponse(this.notaFiscalService.update(notaFiscal));
    } else {
      this.subscribeToSaveResponse(this.notaFiscalService.create(notaFiscal));
    }
  }

  private createFromForm(): INotaFiscal {
    return {
      ...new NotaFiscal(),
      id: this.editForm.get(['id'])!.value,
      numero: this.editForm.get(['numero'])!.value,
      competencia: this.editForm.get(['competencia'])!.value,
      tipo: this.editForm.get(['tipo'])!.value,
      servico: this.editForm.get(['servico'])!.value,
      condicaoPagamento: this.editForm.get(['condicaoPagamento'])!.value,
      totalNF: this.editForm.get(['totalNF'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INotaFiscal>>): void {
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
}
