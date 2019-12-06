import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Speciality } from 'app/shared/model/speciality.model';
import { SpecialityService } from './speciality.service';
import { SpecialityComponent } from './speciality.component';
import { SpecialityDetailComponent } from './speciality-detail.component';
import { SpecialityUpdateComponent } from './speciality-update.component';
import { ISpeciality } from 'app/shared/model/speciality.model';

@Injectable({ providedIn: 'root' })
export class SpecialityResolve implements Resolve<ISpeciality> {
  constructor(private service: SpecialityService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISpeciality> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((speciality: HttpResponse<Speciality>) => speciality.body));
    }
    return of(new Speciality());
  }
}

export const specialityRoute: Routes = [
  {
    path: '',
    component: SpecialityComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.speciality.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SpecialityDetailComponent,
    resolve: {
      speciality: SpecialityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.speciality.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SpecialityUpdateComponent,
    resolve: {
      speciality: SpecialityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.speciality.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SpecialityUpdateComponent,
    resolve: {
      speciality: SpecialityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.speciality.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
