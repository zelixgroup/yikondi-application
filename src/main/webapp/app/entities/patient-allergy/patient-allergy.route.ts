import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PatientAllergy } from 'app/shared/model/patient-allergy.model';
import { PatientAllergyService } from './patient-allergy.service';
import { PatientAllergyComponent } from './patient-allergy.component';
import { PatientAllergyDetailComponent } from './patient-allergy-detail.component';
import { PatientAllergyUpdateComponent } from './patient-allergy-update.component';
import { IPatientAllergy } from 'app/shared/model/patient-allergy.model';

@Injectable({ providedIn: 'root' })
export class PatientAllergyResolve implements Resolve<IPatientAllergy> {
  constructor(private service: PatientAllergyService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPatientAllergy> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((patientAllergy: HttpResponse<PatientAllergy>) => patientAllergy.body));
    }
    return of(new PatientAllergy());
  }
}

export const patientAllergyRoute: Routes = [
  {
    path: '',
    component: PatientAllergyComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.patientAllergy.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PatientAllergyDetailComponent,
    resolve: {
      patientAllergy: PatientAllergyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.patientAllergy.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PatientAllergyUpdateComponent,
    resolve: {
      patientAllergy: PatientAllergyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.patientAllergy.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PatientAllergyUpdateComponent,
    resolve: {
      patientAllergy: PatientAllergyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.patientAllergy.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
