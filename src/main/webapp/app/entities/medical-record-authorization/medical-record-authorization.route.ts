import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { MedicalRecordAuthorization } from 'app/shared/model/medical-record-authorization.model';
import { MedicalRecordAuthorizationService } from './medical-record-authorization.service';
import { MedicalRecordAuthorizationComponent } from './medical-record-authorization.component';
import { MedicalRecordAuthorizationDetailComponent } from './medical-record-authorization-detail.component';
import { MedicalRecordAuthorizationUpdateComponent } from './medical-record-authorization-update.component';
import { IMedicalRecordAuthorization } from 'app/shared/model/medical-record-authorization.model';

@Injectable({ providedIn: 'root' })
export class MedicalRecordAuthorizationResolve implements Resolve<IMedicalRecordAuthorization> {
  constructor(private service: MedicalRecordAuthorizationService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMedicalRecordAuthorization> {
    const id = route.params['id'];
    if (id) {
      return this.service
        .find(id)
        .pipe(map((medicalRecordAuthorization: HttpResponse<MedicalRecordAuthorization>) => medicalRecordAuthorization.body));
    }
    return of(new MedicalRecordAuthorization());
  }
}

export const medicalRecordAuthorizationRoute: Routes = [
  {
    path: '',
    component: MedicalRecordAuthorizationComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.medicalRecordAuthorization.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MedicalRecordAuthorizationDetailComponent,
    resolve: {
      medicalRecordAuthorization: MedicalRecordAuthorizationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.medicalRecordAuthorization.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MedicalRecordAuthorizationUpdateComponent,
    resolve: {
      medicalRecordAuthorization: MedicalRecordAuthorizationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.medicalRecordAuthorization.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MedicalRecordAuthorizationUpdateComponent,
    resolve: {
      medicalRecordAuthorization: MedicalRecordAuthorizationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.medicalRecordAuthorization.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
