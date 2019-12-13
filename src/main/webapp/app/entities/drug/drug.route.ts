import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Drug } from 'app/shared/model/drug.model';
import { DrugService } from './drug.service';
import { DrugComponent } from './drug.component';
import { DrugDetailComponent } from './drug-detail.component';
import { DrugUpdateComponent } from './drug-update.component';
import { IDrug } from 'app/shared/model/drug.model';

@Injectable({ providedIn: 'root' })
export class DrugResolve implements Resolve<IDrug> {
  constructor(private service: DrugService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDrug> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((drug: HttpResponse<Drug>) => drug.body));
    }
    return of(new Drug());
  }
}

export const drugRoute: Routes = [
  {
    path: '',
    component: DrugComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.drug.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DrugDetailComponent,
    resolve: {
      drug: DrugResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.drug.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DrugUpdateComponent,
    resolve: {
      drug: DrugResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.drug.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DrugUpdateComponent,
    resolve: {
      drug: DrugResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.drug.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
