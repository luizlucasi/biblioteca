import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INotaFiscal } from 'app/shared/model/nota-fiscal.model';

@Component({
  selector: 'jhi-nota-fiscal-detail',
  templateUrl: './nota-fiscal-detail.component.html'
})
export class NotaFiscalDetailComponent implements OnInit {
  notaFiscal: INotaFiscal | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ notaFiscal }) => (this.notaFiscal = notaFiscal));
  }

  previousState(): void {
    window.history.back();
  }
}
