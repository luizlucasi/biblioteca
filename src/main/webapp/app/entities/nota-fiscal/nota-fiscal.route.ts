import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { INotaFiscal, NotaFiscal } from 'app/shared/model/nota-fiscal.model';
import { NotaFiscalService } from './nota-fiscal.service';
import { NotaFiscalComponent } from './nota-fiscal.component';
import { NotaFiscalDetailComponent } from './nota-fiscal-detail.component';
import { NotaFiscalUpdateComponent } from './nota-fiscal-update.component';

@Injectable({ providedIn: 'root' })
export class NotaFiscalResolve implements Resolve<INotaFiscal> {
  constructor(private service: NotaFiscalService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INotaFiscal> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((notaFiscal: HttpResponse<NotaFiscal>) => {
          if (notaFiscal.body) {
            return of(notaFiscal.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new NotaFiscal());
  }
}

export const notaFiscalRoute: Routes = [
  {
    path: '',
    component: NotaFiscalComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'libraryApp.notaFiscal.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: NotaFiscalDetailComponent,
    resolve: {
      notaFiscal: NotaFiscalResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'libraryApp.notaFiscal.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: NotaFiscalUpdateComponent,
    resolve: {
      notaFiscal: NotaFiscalResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'libraryApp.notaFiscal.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: NotaFiscalUpdateComponent,
    resolve: {
      notaFiscal: NotaFiscalResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'libraryApp.notaFiscal.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
