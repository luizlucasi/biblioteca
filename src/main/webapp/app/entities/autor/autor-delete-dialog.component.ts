import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAutor } from 'app/shared/model/autor.model';
import { AutorService } from './autor.service';

@Component({
  templateUrl: './autor-delete-dialog.component.html'
})
export class AutorDeleteDialogComponent {
  autor?: IAutor;

  constructor(protected autorService: AutorService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.autorService.delete(id).subscribe(() => {
      this.eventManager.broadcast('autorListModification');
      this.activeModal.close();
    });
  }
}
