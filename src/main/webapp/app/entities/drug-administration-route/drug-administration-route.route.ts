import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { DrugAdministrationRoute } from 'app/shared/model/drug-administration-route.model';
import { DrugAdministrationRouteService } from './drug-administration-route.service';
import { DrugAdministrationRouteComponent } from './drug-administration-route.component';
import { DrugAdministrationRouteDetailComponent } from './drug-administration-route-detail.component';
import { DrugAdministrationRouteUpdateComponent } from './drug-administration-route-update.component';
import { IDrugAdministrationRoute } from 'app/shared/model/drug-administration-route.model';

@Injectable({ providedIn: 'root' })
export class DrugAdministrationRouteResolve implements Resolve<IDrugAdministrationRoute> {
  constructor(private service: DrugAdministrationRouteService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDrugAdministrationRoute> {
    const id = route.params['id'];
    if (id) {
      return this.service
        .find(id)
        .pipe(map((drugAdministrationRoute: HttpResponse<DrugAdministrationRoute>) => drugAdministrationRoute.body));
    }
    return of(new DrugAdministrationRoute());
  }
}

export const drugAdministrationRouteRoute: Routes = [
  {
    path: '',
    component: DrugAdministrationRouteComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.drugAdministrationRoute.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DrugAdministrationRouteDetailComponent,
    resolve: {
      drugAdministrationRoute: DrugAdministrationRouteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.drugAdministrationRoute.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DrugAdministrationRouteUpdateComponent,
    resolve: {
      drugAdministrationRoute: DrugAdministrationRouteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.drugAdministrationRoute.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DrugAdministrationRouteUpdateComponent,
    resolve: {
      drugAdministrationRoute: DrugAdministrationRouteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.drugAdministrationRoute.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
