import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PatientAppointement } from 'app/shared/model/patient-appointement.model';
import { PatientAppointementService } from './patient-appointement.service';
import { PatientAppointementComponent } from './patient-appointement.component';
import { PatientAppointementDetailComponent } from './patient-appointement-detail.component';
import { PatientAppointementUpdateComponent } from './patient-appointement-update.component';
import { IPatientAppointement } from 'app/shared/model/patient-appointement.model';

@Injectable({ providedIn: 'root' })
export class PatientAppointementResolve implements Resolve<IPatientAppointement> {
  constructor(private service: PatientAppointementService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPatientAppointement> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((patientAppointement: HttpResponse<PatientAppointement>) => patientAppointement.body));
    }
    return of(new PatientAppointement());
  }
}

export const patientAppointementRoute: Routes = [
  {
    path: '',
    component: PatientAppointementComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.patientAppointement.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PatientAppointementDetailComponent,
    resolve: {
      patientAppointement: PatientAppointementResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.patientAppointement.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PatientAppointementUpdateComponent,
    resolve: {
      patientAppointement: PatientAppointementResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.patientAppointement.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PatientAppointementUpdateComponent,
    resolve: {
      patientAppointement: PatientAppointementResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.patientAppointement.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
