import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { DoctorSchedule } from 'app/shared/model/doctor-schedule.model';
import { DoctorScheduleService } from './doctor-schedule.service';
import { DoctorScheduleComponent } from './doctor-schedule.component';
import { DoctorScheduleDetailComponent } from './doctor-schedule-detail.component';
import { DoctorScheduleUpdateComponent } from './doctor-schedule-update.component';
import { IDoctorSchedule } from 'app/shared/model/doctor-schedule.model';

@Injectable({ providedIn: 'root' })
export class DoctorScheduleResolve implements Resolve<IDoctorSchedule> {
  constructor(private service: DoctorScheduleService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDoctorSchedule> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((doctorSchedule: HttpResponse<DoctorSchedule>) => doctorSchedule.body));
    }
    return of(new DoctorSchedule());
  }
}

export const doctorScheduleRoute: Routes = [
  {
    path: '',
    component: DoctorScheduleComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.doctorSchedule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DoctorScheduleDetailComponent,
    resolve: {
      doctorSchedule: DoctorScheduleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.doctorSchedule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DoctorScheduleUpdateComponent,
    resolve: {
      doctorSchedule: DoctorScheduleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.doctorSchedule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DoctorScheduleUpdateComponent,
    resolve: {
      doctorSchedule: DoctorScheduleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.doctorSchedule.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
