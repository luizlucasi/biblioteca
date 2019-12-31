import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAluno, Aluno } from 'app/shared/model/aluno.model';
import { AlunoService } from './aluno.service';
import { AlunoComponent } from './aluno.component';
import { AlunoDetailComponent } from './aluno-detail.component';
import { AlunoUpdateComponent } from './aluno-update.component';

@Injectable({ providedIn: 'root' })
export class AlunoResolve implements Resolve<IAluno> {
  constructor(private service: AlunoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAluno> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((aluno: HttpResponse<Aluno>) => {
          if (aluno.body) {
            return of(aluno.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Aluno());
  }
}

export const alunoRoute: Routes = [
  {
    path: '',
    component: AlunoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'libraryApp.aluno.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AlunoDetailComponent,
    resolve: {
      aluno: AlunoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'libraryApp.aluno.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AlunoUpdateComponent,
    resolve: {
      aluno: AlunoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'libraryApp.aluno.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AlunoUpdateComponent,
    resolve: {
      aluno: AlunoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'libraryApp.aluno.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
