import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PatientInsuranceCoverage } from 'app/shared/model/patient-insurance-coverage.model';
import { PatientInsuranceCoverageService } from './patient-insurance-coverage.service';
import { PatientInsuranceCoverageComponent } from './patient-insurance-coverage.component';
import { PatientInsuranceCoverageDetailComponent } from './patient-insurance-coverage-detail.component';
import { PatientInsuranceCoverageUpdateComponent } from './patient-insurance-coverage-update.component';
import { IPatientInsuranceCoverage } from 'app/shared/model/patient-insurance-coverage.model';

@Injectable({ providedIn: 'root' })
export class PatientInsuranceCoverageResolve implements Resolve<IPatientInsuranceCoverage> {
  constructor(private service: PatientInsuranceCoverageService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPatientInsuranceCoverage> {
    const id = route.params['id'];
    if (id) {
      return this.service
        .find(id)
        .pipe(map((patientInsuranceCoverage: HttpResponse<PatientInsuranceCoverage>) => patientInsuranceCoverage.body));
    }
    return of(new PatientInsuranceCoverage());
  }
}

export const patientInsuranceCoverageRoute: Routes = [
  {
    path: '',
    component: PatientInsuranceCoverageComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.patientInsuranceCoverage.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PatientInsuranceCoverageDetailComponent,
    resolve: {
      patientInsuranceCoverage: PatientInsuranceCoverageResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.patientInsuranceCoverage.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PatientInsuranceCoverageUpdateComponent,
    resolve: {
      patientInsuranceCoverage: PatientInsuranceCoverageResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.patientInsuranceCoverage.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PatientInsuranceCoverageUpdateComponent,
    resolve: {
      patientInsuranceCoverage: PatientInsuranceCoverageResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.patientInsuranceCoverage.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
