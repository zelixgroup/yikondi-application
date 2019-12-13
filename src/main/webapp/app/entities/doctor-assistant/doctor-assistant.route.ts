import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { DoctorAssistant } from 'app/shared/model/doctor-assistant.model';
import { DoctorAssistantService } from './doctor-assistant.service';
import { DoctorAssistantComponent } from './doctor-assistant.component';
import { DoctorAssistantDetailComponent } from './doctor-assistant-detail.component';
import { DoctorAssistantUpdateComponent } from './doctor-assistant-update.component';
import { IDoctorAssistant } from 'app/shared/model/doctor-assistant.model';

@Injectable({ providedIn: 'root' })
export class DoctorAssistantResolve implements Resolve<IDoctorAssistant> {
  constructor(private service: DoctorAssistantService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDoctorAssistant> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((doctorAssistant: HttpResponse<DoctorAssistant>) => doctorAssistant.body));
    }
    return of(new DoctorAssistant());
  }
}

export const doctorAssistantRoute: Routes = [
  {
    path: '',
    component: DoctorAssistantComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.doctorAssistant.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DoctorAssistantDetailComponent,
    resolve: {
      doctorAssistant: DoctorAssistantResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.doctorAssistant.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DoctorAssistantUpdateComponent,
    resolve: {
      doctorAssistant: DoctorAssistantResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.doctorAssistant.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DoctorAssistantUpdateComponent,
    resolve: {
      doctorAssistant: DoctorAssistantResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.doctorAssistant.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
