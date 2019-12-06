import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PharmacyAllNightPlanning } from 'app/shared/model/pharmacy-all-night-planning.model';
import { PharmacyAllNightPlanningService } from './pharmacy-all-night-planning.service';
import { PharmacyAllNightPlanningComponent } from './pharmacy-all-night-planning.component';
import { PharmacyAllNightPlanningDetailComponent } from './pharmacy-all-night-planning-detail.component';
import { PharmacyAllNightPlanningUpdateComponent } from './pharmacy-all-night-planning-update.component';
import { IPharmacyAllNightPlanning } from 'app/shared/model/pharmacy-all-night-planning.model';

@Injectable({ providedIn: 'root' })
export class PharmacyAllNightPlanningResolve implements Resolve<IPharmacyAllNightPlanning> {
  constructor(private service: PharmacyAllNightPlanningService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPharmacyAllNightPlanning> {
    const id = route.params['id'];
    if (id) {
      return this.service
        .find(id)
        .pipe(map((pharmacyAllNightPlanning: HttpResponse<PharmacyAllNightPlanning>) => pharmacyAllNightPlanning.body));
    }
    return of(new PharmacyAllNightPlanning());
  }
}

export const pharmacyAllNightPlanningRoute: Routes = [
  {
    path: '',
    component: PharmacyAllNightPlanningComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.pharmacyAllNightPlanning.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PharmacyAllNightPlanningDetailComponent,
    resolve: {
      pharmacyAllNightPlanning: PharmacyAllNightPlanningResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.pharmacyAllNightPlanning.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PharmacyAllNightPlanningUpdateComponent,
    resolve: {
      pharmacyAllNightPlanning: PharmacyAllNightPlanningResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.pharmacyAllNightPlanning.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PharmacyAllNightPlanningUpdateComponent,
    resolve: {
      pharmacyAllNightPlanning: PharmacyAllNightPlanningResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.pharmacyAllNightPlanning.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
