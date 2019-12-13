import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { MedicalPrescription } from 'app/shared/model/medical-prescription.model';
import { MedicalPrescriptionService } from './medical-prescription.service';
import { MedicalPrescriptionComponent } from './medical-prescription.component';
import { MedicalPrescriptionDetailComponent } from './medical-prescription-detail.component';
import { MedicalPrescriptionUpdateComponent } from './medical-prescription-update.component';
import { IMedicalPrescription } from 'app/shared/model/medical-prescription.model';

@Injectable({ providedIn: 'root' })
export class MedicalPrescriptionResolve implements Resolve<IMedicalPrescription> {
  constructor(private service: MedicalPrescriptionService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMedicalPrescription> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((medicalPrescription: HttpResponse<MedicalPrescription>) => medicalPrescription.body));
    }
    return of(new MedicalPrescription());
  }
}

export const medicalPrescriptionRoute: Routes = [
  {
    path: '',
    component: MedicalPrescriptionComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.medicalPrescription.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MedicalPrescriptionDetailComponent,
    resolve: {
      medicalPrescription: MedicalPrescriptionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.medicalPrescription.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MedicalPrescriptionUpdateComponent,
    resolve: {
      medicalPrescription: MedicalPrescriptionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.medicalPrescription.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MedicalPrescriptionUpdateComponent,
    resolve: {
      medicalPrescription: MedicalPrescriptionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.medicalPrescription.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
