import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { IEmprestimo, Emprestimo } from 'app/shared/model/emprestimo.model';
import { EmprestimoService } from './emprestimo.service';
import { ILivro } from 'app/shared/model/livro.model';
import { LivroService } from 'app/entities/livro/livro.service';
import { IAluno } from 'app/shared/model/aluno.model';
import { AlunoService } from 'app/entities/aluno/aluno.service';

type SelectableEntity = ILivro | IAluno;

@Component({
  selector: 'jhi-emprestimo-update',
  templateUrl: './emprestimo-update.component.html'
})
export class EmprestimoUpdateComponent implements OnInit {
  isSaving = false;

  livros: ILivro[] = [];

  alunos: IAluno[] = [];
  dataEmprestimoDp: any;

  editForm = this.fb.group({
    id: [],
    dataEmprestimo: [],
    livro: [],
    aluno: []
  });

  constructor(
    protected emprestimoService: EmprestimoService,
    protected livroService: LivroService,
    protected alunoService: AlunoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ emprestimo }) => {
      this.updateForm(emprestimo);

      this.livroService
        .query({ 'emprestimoId.specified': 'false' })
        .pipe(
          map((res: HttpResponse<ILivro[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: ILivro[]) => {
          if (!emprestimo.livro || !emprestimo.livro.id) {
            this.livros = resBody;
          } else {
            this.livroService
              .find(emprestimo.livro.id)
              .pipe(
                map((subRes: HttpResponse<ILivro>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ILivro[]) => {
                this.livros = concatRes;
              });
          }
        });

      this.alunoService
        .query({ 'emprestimoId.specified': 'false' })
        .pipe(
          map((res: HttpResponse<IAluno[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IAluno[]) => {
          if (!emprestimo.aluno || !emprestimo.aluno.id) {
            this.alunos = resBody;
          } else {
            this.alunoService
              .find(emprestimo.aluno.id)
              .pipe(
                map((subRes: HttpResponse<IAluno>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IAluno[]) => {
                this.alunos = concatRes;
              });
          }
        });
    });
  }

  updateForm(emprestimo: IEmprestimo): void {
    this.editForm.patchValue({
      id: emprestimo.id,
      dataEmprestimo: emprestimo.dataEmprestimo,
      livro: emprestimo.livro,
      aluno: emprestimo.aluno
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const emprestimo = this.createFromForm();
    if (emprestimo.id !== undefined) {
      this.subscribeToSaveResponse(this.emprestimoService.update(emprestimo));
    } else {
      this.subscribeToSaveResponse(this.emprestimoService.create(emprestimo));
    }
  }

  private createFromForm(): IEmprestimo {
    return {
      ...new Emprestimo(),
      id: this.editForm.get(['id'])!.value,
      dataEmprestimo: this.editForm.get(['dataEmprestimo'])!.value,
      livro: this.editForm.get(['livro'])!.value,
      aluno: this.editForm.get(['aluno'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmprestimo>>): void {
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
