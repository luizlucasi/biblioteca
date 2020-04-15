import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICircuito } from 'app/shared/model/circuito.model';

@Component({
  selector: 'jhi-circuito-detail',
  templateUrl: './circuito-detail.component.html'
})
export class CircuitoDetailComponent implements OnInit {
  circuito: ICircuito | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ circuito }) => (this.circuito = circuito));
  }

  previousState(): void {
    window.history.back();
  }
}
