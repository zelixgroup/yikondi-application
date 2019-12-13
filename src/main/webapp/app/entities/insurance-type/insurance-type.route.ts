import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { InsuranceType } from 'app/shared/model/insurance-type.model';
import { InsuranceTypeService } from './insurance-type.service';
import { InsuranceTypeComponent } from './insurance-type.component';
import { InsuranceTypeDetailComponent } from './insurance-type-detail.component';
import { InsuranceTypeUpdateComponent } from './insurance-type-update.component';
import { IInsuranceType } from 'app/shared/model/insurance-type.model';

@Injectable({ providedIn: 'root' })
export class InsuranceTypeResolve implements Resolve<IInsuranceType> {
  constructor(private service: InsuranceTypeService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInsuranceType> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((insuranceType: HttpResponse<InsuranceType>) => insuranceType.body));
    }
    return of(new InsuranceType());
  }
}

export const insuranceTypeRoute: Routes = [
  {
    path: '',
    component: InsuranceTypeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.insuranceType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: InsuranceTypeDetailComponent,
    resolve: {
      insuranceType: InsuranceTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.insuranceType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: InsuranceTypeUpdateComponent,
    resolve: {
      insuranceType: InsuranceTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.insuranceType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: InsuranceTypeUpdateComponent,
    resolve: {
      insuranceType: InsuranceTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.insuranceType.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
