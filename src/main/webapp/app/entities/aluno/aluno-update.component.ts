import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAluno, Aluno } from 'app/shared/model/aluno.model';
import { AlunoService } from './aluno.service';

@Component({
  selector: 'jhi-aluno-update',
  templateUrl: './aluno-update.component.html'
})
export class AlunoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(100)]],
    turma: [],
    matricula: [],
    celular: [null, [Validators.maxLength(20)]]
  });

  constructor(protected alunoService: AlunoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ aluno }) => {
      this.updateForm(aluno);
    });
  }

  updateForm(aluno: IAluno): void {
    this.editForm.patchValue({
      id: aluno.id,
      nome: aluno.nome,
      turma: aluno.turma,
      matricula: aluno.matricula,
      celular: aluno.celular
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const aluno = this.createFromForm();
    if (aluno.id !== undefined) {
      this.subscribeToSaveResponse(this.alunoService.update(aluno));
    } else {
      this.subscribeToSaveResponse(this.alunoService.create(aluno));
    }
  }

  private createFromForm(): IAluno {
    return {
      ...new Aluno(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      turma: this.editForm.get(['turma'])!.value,
      matricula: this.editForm.get(['matricula'])!.value,
      celular: this.editForm.get(['celular'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAluno>>): void {
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
