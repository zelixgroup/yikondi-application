import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PatientEmergencyNumber } from 'app/shared/model/patient-emergency-number.model';
import { PatientEmergencyNumberService } from './patient-emergency-number.service';
import { PatientEmergencyNumberComponent } from './patient-emergency-number.component';
import { PatientEmergencyNumberDetailComponent } from './patient-emergency-number-detail.component';
import { PatientEmergencyNumberUpdateComponent } from './patient-emergency-number-update.component';
import { IPatientEmergencyNumber } from 'app/shared/model/patient-emergency-number.model';

@Injectable({ providedIn: 'root' })
export class PatientEmergencyNumberResolve implements Resolve<IPatientEmergencyNumber> {
  constructor(private service: PatientEmergencyNumberService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPatientEmergencyNumber> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((patientEmergencyNumber: HttpResponse<PatientEmergencyNumber>) => patientEmergencyNumber.body));
    }
    return of(new PatientEmergencyNumber());
  }
}

export const patientEmergencyNumberRoute: Routes = [
  {
    path: '',
    component: PatientEmergencyNumberComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.patientEmergencyNumber.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PatientEmergencyNumberDetailComponent,
    resolve: {
      patientEmergencyNumber: PatientEmergencyNumberResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.patientEmergencyNumber.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PatientEmergencyNumberUpdateComponent,
    resolve: {
      patientEmergencyNumber: PatientEmergencyNumberResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.patientEmergencyNumber.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PatientEmergencyNumberUpdateComponent,
    resolve: {
      patientEmergencyNumber: PatientEmergencyNumberResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.patientEmergencyNumber.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
