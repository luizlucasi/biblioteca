import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAluno } from 'app/shared/model/aluno.model';
import { AlunoService } from './aluno.service';

@Component({
  templateUrl: './aluno-delete-dialog.component.html'
})
export class AlunoDeleteDialogComponent {
  aluno?: IAluno;

  constructor(protected alunoService: AlunoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.alunoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('alunoListModification');
      this.activeModal.close();
    });
  }
}
