import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { HealthCentreDoctor } from 'app/shared/model/health-centre-doctor.model';
import { HealthCentreDoctorService } from './health-centre-doctor.service';
import { HealthCentreDoctorComponent } from './health-centre-doctor.component';
import { HealthCentreDoctorDetailComponent } from './health-centre-doctor-detail.component';
import { HealthCentreDoctorUpdateComponent } from './health-centre-doctor-update.component';
import { IHealthCentreDoctor } from 'app/shared/model/health-centre-doctor.model';

@Injectable({ providedIn: 'root' })
export class HealthCentreDoctorResolve implements Resolve<IHealthCentreDoctor> {
  constructor(private service: HealthCentreDoctorService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHealthCentreDoctor> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((healthCentreDoctor: HttpResponse<HealthCentreDoctor>) => healthCentreDoctor.body));
    }
    return of(new HealthCentreDoctor());
  }
}

export const healthCentreDoctorRoute: Routes = [
  {
    path: '',
    component: HealthCentreDoctorComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.healthCentreDoctor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: HealthCentreDoctorDetailComponent,
    resolve: {
      healthCentreDoctor: HealthCentreDoctorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.healthCentreDoctor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: HealthCentreDoctorUpdateComponent,
    resolve: {
      healthCentreDoctor: HealthCentreDoctorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.healthCentreDoctor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: HealthCentreDoctorUpdateComponent,
    resolve: {
      healthCentreDoctor: HealthCentreDoctorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.healthCentreDoctor.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
