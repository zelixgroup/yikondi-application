import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Holiday } from 'app/shared/model/holiday.model';
import { HolidayService } from './holiday.service';
import { HolidayComponent } from './holiday.component';
import { HolidayDetailComponent } from './holiday-detail.component';
import { HolidayUpdateComponent } from './holiday-update.component';
import { IHoliday } from 'app/shared/model/holiday.model';

@Injectable({ providedIn: 'root' })
export class HolidayResolve implements Resolve<IHoliday> {
  constructor(private service: HolidayService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHoliday> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((holiday: HttpResponse<Holiday>) => holiday.body));
    }
    return of(new Holiday());
  }
}

export const holidayRoute: Routes = [
  {
    path: '',
    component: HolidayComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.holiday.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: HolidayDetailComponent,
    resolve: {
      holiday: HolidayResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.holiday.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: HolidayUpdateComponent,
    resolve: {
      holiday: HolidayResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.holiday.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: HolidayUpdateComponent,
    resolve: {
      holiday: HolidayResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.holiday.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
