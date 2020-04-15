import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { INotaFiscal } from 'app/shared/model/nota-fiscal.model';
import { NotaFiscalService } from './nota-fiscal.service';
import { NotaFiscalDeleteDialogComponent } from './nota-fiscal-delete-dialog.component';

@Component({
  selector: 'jhi-nota-fiscal',
  templateUrl: './nota-fiscal.component.html'
})
export class NotaFiscalComponent implements OnInit, OnDestroy {
  notaFiscals?: INotaFiscal[];
  eventSubscriber?: Subscription;

  constructor(protected notaFiscalService: NotaFiscalService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.notaFiscalService.query().subscribe((res: HttpResponse<INotaFiscal[]>) => (this.notaFiscals = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInNotaFiscals();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: INotaFiscal): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInNotaFiscals(): void {
    this.eventSubscriber = this.eventManager.subscribe('notaFiscalListModification', () => this.loadAll());
  }

  delete(notaFiscal: INotaFiscal): void {
    const modalRef = this.modalService.open(NotaFiscalDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.notaFiscal = notaFiscal;
  }
}
