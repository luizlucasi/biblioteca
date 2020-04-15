import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INotaFiscal } from 'app/shared/model/nota-fiscal.model';
import { NotaFiscalService } from './nota-fiscal.service';

@Component({
  templateUrl: './nota-fiscal-delete-dialog.component.html'
})
export class NotaFiscalDeleteDialogComponent {
  notaFiscal?: INotaFiscal;

  constructor(
    protected notaFiscalService: NotaFiscalService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.notaFiscalService.delete(id).subscribe(() => {
      this.eventManager.broadcast('notaFiscalListModification');
      this.activeModal.close();
    });
  }
}
