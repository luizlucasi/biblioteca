import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICircuito } from 'app/shared/model/circuito.model';
import { CircuitoService } from './circuito.service';

@Component({
  templateUrl: './circuito-delete-dialog.component.html'
})
export class CircuitoDeleteDialogComponent {
  circuito?: ICircuito;

  constructor(protected circuitoService: CircuitoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.circuitoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('circuitoListModification');
      this.activeModal.close();
    });
  }
}
