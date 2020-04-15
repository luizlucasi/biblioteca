import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICircuito, Circuito } from 'app/shared/model/circuito.model';
import { CircuitoService } from './circuito.service';

@Component({
  selector: 'jhi-circuito-update',
  templateUrl: './circuito-update.component.html'
})
export class CircuitoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    idControleDesignacao: [],
    idEstacao: []
  });

  constructor(protected circuitoService: CircuitoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ circuito }) => {
      this.updateForm(circuito);
    });
  }

  updateForm(circuito: ICircuito): void {
    this.editForm.patchValue({
      id: circuito.id,
      idControleDesignacao: circuito.idControleDesignacao,
      idEstacao: circuito.idEstacao
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const circuito = this.createFromForm();
    if (circuito.id !== undefined) {
      this.subscribeToSaveResponse(this.circuitoService.update(circuito));
    } else {
      this.subscribeToSaveResponse(this.circuitoService.create(circuito));
    }
  }

  private createFromForm(): ICircuito {
    return {
      ...new Circuito(),
      id: this.editForm.get(['id'])!.value,
      idControleDesignacao: this.editForm.get(['idControleDesignacao'])!.value,
      idEstacao: this.editForm.get(['idEstacao'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICircuito>>): void {
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
