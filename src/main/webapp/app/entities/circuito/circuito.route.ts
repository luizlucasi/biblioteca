import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICircuito, Circuito } from 'app/shared/model/circuito.model';
import { CircuitoService } from './circuito.service';
import { CircuitoComponent } from './circuito.component';
import { CircuitoDetailComponent } from './circuito-detail.component';
import { CircuitoUpdateComponent } from './circuito-update.component';

@Injectable({ providedIn: 'root' })
export class CircuitoResolve implements Resolve<ICircuito> {
  constructor(private service: CircuitoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICircuito> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((circuito: HttpResponse<Circuito>) => {
          if (circuito.body) {
            return of(circuito.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Circuito());
  }
}

export const circuitoRoute: Routes = [
  {
    path: '',
    component: CircuitoComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'libraryApp.circuito.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CircuitoDetailComponent,
    resolve: {
      circuito: CircuitoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'libraryApp.circuito.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CircuitoUpdateComponent,
    resolve: {
      circuito: CircuitoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'libraryApp.circuito.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CircuitoUpdateComponent,
    resolve: {
      circuito: CircuitoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'libraryApp.circuito.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
