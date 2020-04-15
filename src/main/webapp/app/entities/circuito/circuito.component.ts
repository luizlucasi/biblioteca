import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICircuito } from 'app/shared/model/circuito.model';
import { CircuitoService } from './circuito.service';
import { CircuitoDeleteDialogComponent } from './circuito-delete-dialog.component';

@Component({
  selector: 'jhi-circuito',
  templateUrl: './circuito.component.html'
})
export class CircuitoComponent implements OnInit, OnDestroy {
  circuitos?: ICircuito[];
  eventSubscriber?: Subscription;

  constructor(protected circuitoService: CircuitoService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.circuitoService.query().subscribe((res: HttpResponse<ICircuito[]>) => (this.circuitos = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCircuitos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICircuito): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCircuitos(): void {
    this.eventSubscriber = this.eventManager.subscribe('circuitoListModification', () => this.loadAll());
  }

  delete(circuito: ICircuito): void {
    const modalRef = this.modalService.open(CircuitoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.circuito = circuito;
  }
}
