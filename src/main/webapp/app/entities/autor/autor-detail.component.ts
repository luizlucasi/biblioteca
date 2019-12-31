import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAutor } from 'app/shared/model/autor.model';

@Component({
  selector: 'jhi-autor-detail',
  templateUrl: './autor-detail.component.html'
})
export class AutorDetailComponent implements OnInit {
  autor: IAutor | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ autor }) => {
      this.autor = autor;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
