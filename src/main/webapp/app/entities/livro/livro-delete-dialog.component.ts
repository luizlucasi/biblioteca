import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILivro } from 'app/shared/model/livro.model';
import { LivroService } from './livro.service';

@Component({
  templateUrl: './livro-delete-dialog.component.html'
})
export class LivroDeleteDialogComponent {
  livro?: ILivro;

  constructor(protected livroService: LivroService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.livroService.delete(id).subscribe(() => {
      this.eventManager.broadcast('livroListModification');
      this.activeModal.close();
    });
  }
}
