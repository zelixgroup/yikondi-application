import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { HealthCentreCategory } from 'app/shared/model/health-centre-category.model';
import { HealthCentreCategoryService } from './health-centre-category.service';
import { HealthCentreCategoryComponent } from './health-centre-category.component';
import { HealthCentreCategoryDetailComponent } from './health-centre-category-detail.component';
import { HealthCentreCategoryUpdateComponent } from './health-centre-category-update.component';
import { IHealthCentreCategory } from 'app/shared/model/health-centre-category.model';

@Injectable({ providedIn: 'root' })
export class HealthCentreCategoryResolve implements Resolve<IHealthCentreCategory> {
  constructor(private service: HealthCentreCategoryService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHealthCentreCategory> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((healthCentreCategory: HttpResponse<HealthCentreCategory>) => healthCentreCategory.body));
    }
    return of(new HealthCentreCategory());
  }
}

export const healthCentreCategoryRoute: Routes = [
  {
    path: '',
    component: HealthCentreCategoryComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.healthCentreCategory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: HealthCentreCategoryDetailComponent,
    resolve: {
      healthCentreCategory: HealthCentreCategoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.healthCentreCategory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: HealthCentreCategoryUpdateComponent,
    resolve: {
      healthCentreCategory: HealthCentreCategoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.healthCentreCategory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: HealthCentreCategoryUpdateComponent,
    resolve: {
      healthCentreCategory: HealthCentreCategoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.healthCentreCategory.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
