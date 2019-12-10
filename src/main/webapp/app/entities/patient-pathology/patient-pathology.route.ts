import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PatientPathology } from 'app/shared/model/patient-pathology.model';
import { PatientPathologyService } from './patient-pathology.service';
import { PatientPathologyComponent } from './patient-pathology.component';
import { PatientPathologyDetailComponent } from './patient-pathology-detail.component';
import { PatientPathologyUpdateComponent } from './patient-pathology-update.component';
import { IPatientPathology } from 'app/shared/model/patient-pathology.model';

@Injectable({ providedIn: 'root' })
export class PatientPathologyResolve implements Resolve<IPatientPathology> {
  constructor(private service: PatientPathologyService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPatientPathology> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((patientPathology: HttpResponse<PatientPathology>) => patientPathology.body));
    }
    return of(new PatientPathology());
  }
}

export const patientPathologyRoute: Routes = [
  {
    path: '',
    component: PatientPathologyComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.patientPathology.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PatientPathologyDetailComponent,
    resolve: {
      patientPathology: PatientPathologyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.patientPathology.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PatientPathologyUpdateComponent,
    resolve: {
      patientPathology: PatientPathologyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.patientPathology.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PatientPathologyUpdateComponent,
    resolve: {
      patientPathology: PatientPathologyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.patientPathology.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
