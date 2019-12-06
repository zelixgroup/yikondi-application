import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { HealthCentre } from 'app/shared/model/health-centre.model';
import { HealthCentreService } from './health-centre.service';
import { HealthCentreComponent } from './health-centre.component';
import { HealthCentreDetailComponent } from './health-centre-detail.component';
import { HealthCentreUpdateComponent } from './health-centre-update.component';
import { IHealthCentre } from 'app/shared/model/health-centre.model';

@Injectable({ providedIn: 'root' })
export class HealthCentreResolve implements Resolve<IHealthCentre> {
  constructor(private service: HealthCentreService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHealthCentre> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((healthCentre: HttpResponse<HealthCentre>) => healthCentre.body));
    }
    return of(new HealthCentre());
  }
}

export const healthCentreRoute: Routes = [
  {
    path: '',
    component: HealthCentreComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.healthCentre.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: HealthCentreDetailComponent,
    resolve: {
      healthCentre: HealthCentreResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.healthCentre.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: HealthCentreUpdateComponent,
    resolve: {
      healthCentre: HealthCentreResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.healthCentre.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: HealthCentreUpdateComponent,
    resolve: {
      healthCentre: HealthCentreResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.healthCentre.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
