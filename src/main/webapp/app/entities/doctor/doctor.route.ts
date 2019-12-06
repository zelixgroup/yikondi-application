import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Doctor } from 'app/shared/model/doctor.model';
import { DoctorService } from './doctor.service';
import { DoctorComponent } from './doctor.component';
import { DoctorDetailComponent } from './doctor-detail.component';
import { DoctorUpdateComponent } from './doctor-update.component';
import { IDoctor } from 'app/shared/model/doctor.model';

@Injectable({ providedIn: 'root' })
export class DoctorResolve implements Resolve<IDoctor> {
  constructor(private service: DoctorService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDoctor> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((doctor: HttpResponse<Doctor>) => doctor.body));
    }
    return of(new Doctor());
  }
}

export const doctorRoute: Routes = [
  {
    path: '',
    component: DoctorComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.doctor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DoctorDetailComponent,
    resolve: {
      doctor: DoctorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.doctor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DoctorUpdateComponent,
    resolve: {
      doctor: DoctorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.doctor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DoctorUpdateComponent,
    resolve: {
      doctor: DoctorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.doctor.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
