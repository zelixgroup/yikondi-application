import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PatientFavoritePharmacy } from 'app/shared/model/patient-favorite-pharmacy.model';
import { PatientFavoritePharmacyService } from './patient-favorite-pharmacy.service';
import { PatientFavoritePharmacyComponent } from './patient-favorite-pharmacy.component';
import { PatientFavoritePharmacyDetailComponent } from './patient-favorite-pharmacy-detail.component';
import { PatientFavoritePharmacyUpdateComponent } from './patient-favorite-pharmacy-update.component';
import { IPatientFavoritePharmacy } from 'app/shared/model/patient-favorite-pharmacy.model';

@Injectable({ providedIn: 'root' })
export class PatientFavoritePharmacyResolve implements Resolve<IPatientFavoritePharmacy> {
  constructor(private service: PatientFavoritePharmacyService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPatientFavoritePharmacy> {
    const id = route.params['id'];
    if (id) {
      return this.service
        .find(id)
        .pipe(map((patientFavoritePharmacy: HttpResponse<PatientFavoritePharmacy>) => patientFavoritePharmacy.body));
    }
    return of(new PatientFavoritePharmacy());
  }
}

export const patientFavoritePharmacyRoute: Routes = [
  {
    path: '',
    component: PatientFavoritePharmacyComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.patientFavoritePharmacy.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PatientFavoritePharmacyDetailComponent,
    resolve: {
      patientFavoritePharmacy: PatientFavoritePharmacyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.patientFavoritePharmacy.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PatientFavoritePharmacyUpdateComponent,
    resolve: {
      patientFavoritePharmacy: PatientFavoritePharmacyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.patientFavoritePharmacy.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PatientFavoritePharmacyUpdateComponent,
    resolve: {
      patientFavoritePharmacy: PatientFavoritePharmacyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.patientFavoritePharmacy.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
