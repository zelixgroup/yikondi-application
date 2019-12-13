import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { MedicalPrescriptionDrug } from 'app/shared/model/medical-prescription-drug.model';
import { MedicalPrescriptionDrugService } from './medical-prescription-drug.service';
import { MedicalPrescriptionDrugComponent } from './medical-prescription-drug.component';
import { MedicalPrescriptionDrugDetailComponent } from './medical-prescription-drug-detail.component';
import { MedicalPrescriptionDrugUpdateComponent } from './medical-prescription-drug-update.component';
import { IMedicalPrescriptionDrug } from 'app/shared/model/medical-prescription-drug.model';

@Injectable({ providedIn: 'root' })
export class MedicalPrescriptionDrugResolve implements Resolve<IMedicalPrescriptionDrug> {
  constructor(private service: MedicalPrescriptionDrugService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMedicalPrescriptionDrug> {
    const id = route.params['id'];
    if (id) {
      return this.service
        .find(id)
        .pipe(map((medicalPrescriptionDrug: HttpResponse<MedicalPrescriptionDrug>) => medicalPrescriptionDrug.body));
    }
    return of(new MedicalPrescriptionDrug());
  }
}

export const medicalPrescriptionDrugRoute: Routes = [
  {
    path: '',
    component: MedicalPrescriptionDrugComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.medicalPrescriptionDrug.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MedicalPrescriptionDrugDetailComponent,
    resolve: {
      medicalPrescriptionDrug: MedicalPrescriptionDrugResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.medicalPrescriptionDrug.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MedicalPrescriptionDrugUpdateComponent,
    resolve: {
      medicalPrescriptionDrug: MedicalPrescriptionDrugResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.medicalPrescriptionDrug.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MedicalPrescriptionDrugUpdateComponent,
    resolve: {
      medicalPrescriptionDrug: MedicalPrescriptionDrugResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'yikondiApp.medicalPrescriptionDrug.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
