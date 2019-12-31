import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILivro, Livro } from 'app/shared/model/livro.model';
import { LivroService } from './livro.service';
import { LivroComponent } from './livro.component';
import { LivroDetailComponent } from './livro-detail.component';
import { LivroUpdateComponent } from './livro-update.component';

@Injectable({ providedIn: 'root' })
export class LivroResolve implements Resolve<ILivro> {
  constructor(private service: LivroService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILivro> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((livro: HttpResponse<Livro>) => {
          if (livro.body) {
            return of(livro.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Livro());
  }
}

export const livroRoute: Routes = [
  {
    path: '',
    component: LivroComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'libraryApp.livro.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LivroDetailComponent,
    resolve: {
      livro: LivroResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'libraryApp.livro.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LivroUpdateComponent,
    resolve: {
      livro: LivroResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'libraryApp.livro.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LivroUpdateComponent,
    resolve: {
      livro: LivroResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'libraryApp.livro.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
