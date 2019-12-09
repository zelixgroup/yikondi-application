import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { EmergencyAmbulance } from 'app/shared/model/emergency-ambulance.model';
import { EmergencyAmbulanceService } from './emergency-ambulance.service';
import { EmergencyAmbulanceComponent } from './emergency-ambulance.component';
import { EmergencyAmbulanceDetailComponent } from './emergency-ambulance-detail.component';
import { EmergencyAmbulanceUpdateComponent } from './emergency-ambulance-update.component';
import { IEmergencyAmbulance } from 'app/shared/model/emergency-ambulance.model';

@Injectable({ providedIn: 'root' })
export class EmergencyAmbulanceResolve implements Resolve<IEmergencyAmbulance> {
  constructor(private service: EmergencyAmbulanceService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEmergencyAmbulance> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((emergencyAmbulance: HttpResponse<EmergencyAmbulance>) => emergencyAmbulance.body));
    }
    return of(new EmergencyAmbulance());
  }
}

export const emergencyAmbulanceRoute: Routes = [
  {
    path: '',
    component: EmergencyAmbulanceComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.emergencyAmbulance.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EmergencyAmbulanceDetailComponent,
    resolve: {
      emergencyAmbulance: EmergencyAmbulanceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.emergencyAmbulance.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EmergencyAmbulanceUpdateComponent,
    resolve: {
      emergencyAmbulance: EmergencyAmbulanceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.emergencyAmbulance.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EmergencyAmbulanceUpdateComponent,
    resolve: {
      emergencyAmbulance: EmergencyAmbulanceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.emergencyAmbulance.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
