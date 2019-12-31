import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEmprestimo } from 'app/shared/model/emprestimo.model';
import { EmprestimoService } from './emprestimo.service';

@Component({
  templateUrl: './emprestimo-delete-dialog.component.html'
})
export class EmprestimoDeleteDialogComponent {
  emprestimo?: IEmprestimo;

  constructor(
    protected emprestimoService: EmprestimoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.emprestimoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('emprestimoListModification');
      this.activeModal.close();
    });
  }
}
