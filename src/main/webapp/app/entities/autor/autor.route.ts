import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAutor, Autor } from 'app/shared/model/autor.model';
import { AutorService } from './autor.service';
import { AutorComponent } from './autor.component';
import { AutorDetailComponent } from './autor-detail.component';
import { AutorUpdateComponent } from './autor-update.component';

@Injectable({ providedIn: 'root' })
export class AutorResolve implements Resolve<IAutor> {
  constructor(private service: AutorService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAutor> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((autor: HttpResponse<Autor>) => {
          if (autor.body) {
            return of(autor.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Autor());
  }
}

export const autorRoute: Routes = [
  {
    path: '',
    component: AutorComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'libraryApp.autor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AutorDetailComponent,
    resolve: {
      autor: AutorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'libraryApp.autor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AutorUpdateComponent,
    resolve: {
      autor: AutorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'libraryApp.autor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AutorUpdateComponent,
    resolve: {
      autor: AutorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'libraryApp.autor.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
