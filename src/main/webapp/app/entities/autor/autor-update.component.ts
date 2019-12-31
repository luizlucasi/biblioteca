import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAutor, Autor } from 'app/shared/model/autor.model';
import { AutorService } from './autor.service';

@Component({
  selector: 'jhi-autor-update',
  templateUrl: './autor-update.component.html'
})
export class AutorUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(100)]],
    sobrenome: [null, [Validators.required, Validators.maxLength(100)]]
  });

  constructor(protected autorService: AutorService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ autor }) => {
      this.updateForm(autor);
    });
  }

  updateForm(autor: IAutor): void {
    this.editForm.patchValue({
      id: autor.id,
      nome: autor.nome,
      sobrenome: autor.sobrenome
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const autor = this.createFromForm();
    if (autor.id !== undefined) {
      this.subscribeToSaveResponse(this.autorService.update(autor));
    } else {
      this.subscribeToSaveResponse(this.autorService.create(autor));
    }
  }

  private createFromForm(): IAutor {
    return {
      ...new Autor(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      sobrenome: this.editForm.get(['sobrenome'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAutor>>): void {
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
