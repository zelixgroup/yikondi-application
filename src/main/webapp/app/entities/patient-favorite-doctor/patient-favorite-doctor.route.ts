import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PatientFavoriteDoctor } from 'app/shared/model/patient-favorite-doctor.model';
import { PatientFavoriteDoctorService } from './patient-favorite-doctor.service';
import { PatientFavoriteDoctorComponent } from './patient-favorite-doctor.component';
import { PatientFavoriteDoctorDetailComponent } from './patient-favorite-doctor-detail.component';
import { PatientFavoriteDoctorUpdateComponent } from './patient-favorite-doctor-update.component';
import { IPatientFavoriteDoctor } from 'app/shared/model/patient-favorite-doctor.model';

@Injectable({ providedIn: 'root' })
export class PatientFavoriteDoctorResolve implements Resolve<IPatientFavoriteDoctor> {
  constructor(private service: PatientFavoriteDoctorService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPatientFavoriteDoctor> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((patientFavoriteDoctor: HttpResponse<PatientFavoriteDoctor>) => patientFavoriteDoctor.body));
    }
    return of(new PatientFavoriteDoctor());
  }
}

export const patientFavoriteDoctorRoute: Routes = [
  {
    path: '',
    component: PatientFavoriteDoctorComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.patientFavoriteDoctor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PatientFavoriteDoctorDetailComponent,
    resolve: {
      patientFavoriteDoctor: PatientFavoriteDoctorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.patientFavoriteDoctor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PatientFavoriteDoctorUpdateComponent,
    resolve: {
      patientFavoriteDoctor: PatientFavoriteDoctorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.patientFavoriteDoctor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PatientFavoriteDoctorUpdateComponent,
    resolve: {
      patientFavoriteDoctor: PatientFavoriteDoctorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.patientFavoriteDoctor.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
