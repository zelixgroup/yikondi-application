import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { DoctorWorkingSlot } from 'app/shared/model/doctor-working-slot.model';
import { DoctorWorkingSlotService } from './doctor-working-slot.service';
import { DoctorWorkingSlotComponent } from './doctor-working-slot.component';
import { DoctorWorkingSlotDetailComponent } from './doctor-working-slot-detail.component';
import { DoctorWorkingSlotUpdateComponent } from './doctor-working-slot-update.component';
import { IDoctorWorkingSlot } from 'app/shared/model/doctor-working-slot.model';

@Injectable({ providedIn: 'root' })
export class DoctorWorkingSlotResolve implements Resolve<IDoctorWorkingSlot> {
  constructor(private service: DoctorWorkingSlotService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDoctorWorkingSlot> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((doctorWorkingSlot: HttpResponse<DoctorWorkingSlot>) => doctorWorkingSlot.body));
    }
    return of(new DoctorWorkingSlot());
  }
}

export const doctorWorkingSlotRoute: Routes = [
  {
    path: '',
    component: DoctorWorkingSlotComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.doctorWorkingSlot.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DoctorWorkingSlotDetailComponent,
    resolve: {
      doctorWorkingSlot: DoctorWorkingSlotResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.doctorWorkingSlot.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DoctorWorkingSlotUpdateComponent,
    resolve: {
      doctorWorkingSlot: DoctorWorkingSlotResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.doctorWorkingSlot.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DoctorWorkingSlotUpdateComponent,
    resolve: {
      doctorWorkingSlot: DoctorWorkingSlotResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.doctorWorkingSlot.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
