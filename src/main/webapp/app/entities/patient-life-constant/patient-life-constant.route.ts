import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PatientLifeConstant } from 'app/shared/model/patient-life-constant.model';
import { PatientLifeConstantService } from './patient-life-constant.service';
import { PatientLifeConstantComponent } from './patient-life-constant.component';
import { PatientLifeConstantDetailComponent } from './patient-life-constant-detail.component';
import { PatientLifeConstantUpdateComponent } from './patient-life-constant-update.component';
import { IPatientLifeConstant } from 'app/shared/model/patient-life-constant.model';

@Injectable({ providedIn: 'root' })
export class PatientLifeConstantResolve implements Resolve<IPatientLifeConstant> {
  constructor(private service: PatientLifeConstantService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPatientLifeConstant> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((patientLifeConstant: HttpResponse<PatientLifeConstant>) => patientLifeConstant.body));
    }
    return of(new PatientLifeConstant());
  }
}

export const patientLifeConstantRoute: Routes = [
  {
    path: '',
    component: PatientLifeConstantComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.patientLifeConstant.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PatientLifeConstantDetailComponent,
    resolve: {
      patientLifeConstant: PatientLifeConstantResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.patientLifeConstant.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PatientLifeConstantUpdateComponent,
    resolve: {
      patientLifeConstant: PatientLifeConstantResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.patientLifeConstant.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PatientLifeConstantUpdateComponent,
    resolve: {
      patientLifeConstant: PatientLifeConstantResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.patientLifeConstant.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
